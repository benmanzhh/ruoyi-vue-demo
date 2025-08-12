<template>
  <div class="app-container">
    <!-- 搜索表单 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="120px">
      <el-form-item label="所属设备ID" prop="equipmentId">
        <el-input
          v-model="queryParams.equipmentId"
          placeholder="请输入所属设备ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="部件名称" prop="componentName">
        <el-input
          v-model="queryParams.componentName"
          placeholder="请输入部件名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="部件ID" prop="componentId">
        <el-input
          v-model="queryParams.componentId"
          placeholder="请输入部件ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="部件状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择部件状态" clearable>
          <el-option
            v-for="dict in dict.type.dev_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="投入时间" prop="installDate">
        <el-date-picker clearable
          v-model="queryParams.installDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择投入时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="实际维护时间" prop="actualMaintenanceDate">
        <el-date-picker clearable
          v-model="queryParams.actualMaintenanceDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择实际维护时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="维护人员ID" prop="personnelId">
        <el-input
          v-model="queryParams.personnelId"
          placeholder="请输入维护人员ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 操作按钮行 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:equipcomponents:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:equipcomponents:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:equipcomponents:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:equipcomponents:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <!-- 核心修正: 重新审查并确认了所有列的定义，确保没有多余或错误的列导致空白 -->
    <el-table v-loading="loading" :data="equipcomponentsList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="部件主键ID" align="center" prop="id" width="100"/>
      <el-table-column label="部件名称" align="center" prop="componentName" width="150" :show-overflow-tooltip="true"/>
      <el-table-column label="部件ID" align="center" prop="componentId" width="150" :show-overflow-tooltip="true"/>
      <el-table-column label="所属设备ID" align="center" prop="equipmentId" width="150"/>
      <el-table-column label="部件状态" align="center" prop="status" width="100">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.dev_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="投入时间" align="center" prop="installDate" width="120">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.installDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="使用年限" align="center" prop="lifespanYears" width="100"/>
      <el-table-column label="最大年限" align="center" prop="maxLifespanDate" width="120">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.maxLifespanDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="质保年限" align="center" prop="warrantyYears" width="100"/>
      <el-table-column label="质保时间" align="center" prop="warrantyEndDate" width="120">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.warrantyEndDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="维修规范" align="center" prop="maintenanceRules" width="150" :show-overflow-tooltip="true"/>
      <el-table-column label="下一次计划维护时间" align="center" prop="nextMaintenanceDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.nextMaintenanceDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="实际维护时间" align="center" prop="actualMaintenanceDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.actualMaintenanceDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="维护人员ID" align="center" prop="personnelId" width="140"/>
      <el-table-column label="备注" align="center" prop="remark" width="150" :show-overflow-tooltip="true"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" fixed="right" width="120">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:equipcomponents:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:equipcomponents:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改设备部件对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="140px">
        <!-- 主表字段 -->
        <el-row>
          <el-col :span="12">
            <el-form-item label="所属设备ID" prop="equipmentId">
              <el-input v-model="form.equipmentId" placeholder="请输入所属设备ID" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="部件名称" prop="componentName">
              <el-input v-model="form.componentName" placeholder="请输入部件名称" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="部件ID" prop="componentId">
              <el-input v-model="form.componentId" placeholder="请输入部件ID" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="部件状态" prop="status">
              <el-select v-model="form.status" placeholder="请选择部件状态">
                <el-option
                  v-for="dict in dict.type.dev_status"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="投入时间" prop="installDate">
              <el-date-picker clearable style="width: 100%"
                v-model="form.installDate"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="请选择投入时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="使用年限(年)" prop="lifespanYears">
              <el-input-number v-model="form.lifespanYears" controls-position="right" :min="0" placeholder="请输入使用年限" style="width: 100%"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="最大年限" prop="maxLifespanDate">
              <el-date-picker clearable disabled style="width: 100%"
                v-model="form.maxLifespanDate"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="后端自动计算">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="质保年限(年)" prop="warrantyYears">
              <el-input-number v-model="form.warrantyYears" controls-position="right" :min="0" placeholder="请输入质保年限" style="width: 100%"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="质保时间" prop="warrantyEndDate">
              <el-date-picker clearable disabled style="width: 100%"
                v-model="form.warrantyEndDate"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="后端自动计算">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
             <el-form-item label="下一次计划维护时间" prop="nextMaintenanceDate">
              <el-date-picker clearable style="width: 100%"
                v-model="form.nextMaintenanceDate"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="请选择">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
             <el-form-item label="实际维护时间" prop="actualMaintenanceDate">
              <el-date-picker clearable style="width: 100%"
                v-model="form.actualMaintenanceDate"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="请选择">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="维护人员(员工id)" prop="personnelId">
              <el-input v-model="form.personnelId" placeholder="请输入维护人员(员工id)" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
            <el-col :span="24">
                <el-form-item label="维修规范" prop="maintenanceRules">
                  <el-input v-model="form.maintenanceRules" type="textarea" placeholder="请输入内容" />
                </el-form-item>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="24">
                <el-form-item label="备注" prop="remark">
                  <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
                </el-form-item>
            </el-col>
        </el-row>

        <!-- 子表区域 -->
        <el-tabs v-model="activeName">
          <el-tab-pane label="维护人员信息" name="personnel">
            <el-table :data="form.devPersonnelList" ref="devPersonnelTable" :row-class-name="rowClassName">
              <el-table-column label="序号" align="center" prop="index" width="50"/>
              <el-table-column label="姓名" prop="name" width="150">
                <template slot-scope="scope">
                  <el-input v-model="scope.row.name" placeholder="请输入姓名" />
                </template>
              </el-table-column>
              <el-table-column label="员工联系方式" prop="contactInfo" width="180">
                <template slot-scope="scope">
                  <el-input v-model="scope.row.contactInfo" placeholder="请输入联系方式" />
                </template>
              </el-table-column>
              <el-table-column label="人员属性" prop="personnelType" width="180">
                <template slot-scope="scope">
                  <el-select v-model="scope.row.personnelType" placeholder="请选择人员属性">
                    <el-option
                      v-for="dict in dict.type.dev_personnel_type"
                      :key="dict.value"
                      :label="dict.label"
                      :value="dict.value"
                    ></el-option>
                  </el-select>
                </template>
              </el-table-column>
              <el-table-column label="操作" align="center" width="100">
                <template slot-scope="scope">
                  <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDeleteDevPersonnel(scope.$index)">删除</el-button>
                </template>
              </el-table-column>
            </el-table>
            <div style="margin-top: 10px;">
              <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAddDevPersonnel">添加人员</el-button>
            </div>
          </el-tab-pane>
          <el-tab-pane label="年度作业计划" name="maintenancePlan">
            <el-table :data="form.devAnnualMaintenancePlanList" ref="devAnnualPlanTable" :row-class-name="rowClassName">
              <el-table-column label="序号" align="center" prop="index" width="50"/>
              <el-table-column label="计划年份" prop="year" width="150">
                <template slot-scope="scope">
                  <el-input v-model="scope.row.year" placeholder="请输入年份" />
                </template>
              </el-table-column>
              <el-table-column label="计划日期" prop="scheduledDate" width="240">
                <template slot-scope="scope">
                  <el-date-picker clearable v-model="scope.row.scheduledDate" type="date" value-format="yyyy-MM-dd" placeholder="请选择计划日期" />
                </template>
              </el-table-column>
              <el-table-column label="计划状态" prop="status" width="180">
                <template slot-scope="scope">
                  <el-select v-model="scope.row.status" placeholder="请选择状态">
                    <el-option
                      v-for="dict in dict.type.dev_status"
                      :key="dict.value"
                      :label="dict.label"
                      :value="dict.value"
                    ></el-option>
                  </el-select>
                </template>
              </el-table-column>
              <el-table-column label="备注" prop="remark">
                <template slot-scope="scope">
                  <el-input v-model="scope.row.remark" placeholder="请输入备注" />
                </template>
              </el-table-column>
              <el-table-column label="操作" align="center" width="100">
                <template slot-scope="scope">
                  <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDeleteDevAnnualPlan(scope.$index)">删除</el-button>
                </template>
              </el-table-column>
            </el-table>
             <div style="margin-top: 10px;">
              <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAddDevAnnualPlan">添加计划</el-button>
            </div>
          </el-tab-pane>
        </el-tabs>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listEquipcomponents, getEquipcomponents, delEquipcomponents, addEquipcomponents, updateEquipcomponents } from "@/api/system/equipcomponents";

export default {
  name: "Equipcomponents",
  dicts: ['dev_status', 'dev_personnel_type'],
  data() {
    return {
      loading: true,
      ids: [],
      single: true,
      multiple: true,
      showSearch: true,
      total: 0,
      equipcomponentsList: [],
      title: "",
      open: false,
      activeName: 'personnel',
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        equipmentId: null,
        componentName: null,
        componentId: null,
        status: null,
        installDate: null,
        actualMaintenanceDate: null,
        personnelId: null,
      },
      form: {},
      rules: {
        equipmentId: [
          { required: true, message: "所属设备ID不能为空", trigger: "blur" }
        ],
        componentName: [
          { required: true, message: "部件名称不能为空", trigger: "blur" }
        ],
        componentId: [
          { required: true, message: "部件ID不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    getList() {
      this.loading = true;
      listEquipcomponents(this.queryParams).then(response => {
        this.equipcomponentsList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    cancel() {
      this.open = false;
      this.reset();
    },
    reset() {
      this.form = {
        id: null,
        equipmentId: null,
        componentName: null,
        componentId: null,
        status: null,
        installDate: null,
        lifespanYears: 0,
        maxLifespanDate: null,
        warrantyYears: 0,
        warrantyEndDate: null,
        maintenanceRules: null,
        rulesFileUrl: null,
        nextMaintenanceDate: null,
        actualMaintenanceDate: null,
        personnelId: null,
        remark: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        devPersonnelList: [],
        devAnnualMaintenancePlanList: []
      };
      this.resetForm("form");
    },
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加设备部件";
    },
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids[0];
      getEquipcomponents(id).then(response => {
        this.form = response.data;
        if (!this.form.devPersonnelList) {
            this.form.devPersonnelList = [];
        }
        if (!this.form.devAnnualMaintenancePlanList) {
            this.form.devAnnualMaintenancePlanList = [];
        }
        this.open = true;
        this.title = "修改设备部件";
      });
    },
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateEquipcomponents(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addEquipcomponents(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除设备部件编号为"' + ids + '"的数据项？').then(function() {
        return delEquipcomponents(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    handleExport() {
      this.download('system/equipcomponents/export', {
        ...this.queryParams
      }, `equipcomponents_${new Date().getTime()}.xlsx`);
    },
    rowClassName({ row, rowIndex }) {
      row.index = rowIndex + 1;
    },
    handleAddDevPersonnel() {
      if (!this.form.devPersonnelList) {
        this.form.devPersonnelList = [];
      }
      this.form.devPersonnelList.push({
        name: "",
        contactInfo: "",
        personnelType: null,
      });
    },
    handleDeleteDevPersonnel(index) {
      this.form.devPersonnelList.splice(index, 1);
    },
    handleAddDevAnnualPlan() {
      if (!this.form.devAnnualMaintenancePlanList) {
        this.form.devAnnualMaintenancePlanList = [];
      }
      this.form.devAnnualMaintenancePlanList.push({
        year: new Date().getFullYear(),
        scheduledDate: null,
        status: null,
        remark: ""
      });
    },
    handleDeleteDevAnnualPlan(index) {
      this.form.devAnnualMaintenancePlanList.splice(index, 1);
    }
  }
};
</script>
