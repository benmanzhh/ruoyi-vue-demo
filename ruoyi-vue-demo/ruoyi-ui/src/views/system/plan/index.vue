<template>
  <div class="app-container">
    <!-- 搜索栏 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="维护设备类型" prop="targetType">
        <el-select v-model="queryParams.targetType" placeholder="请选择维护设备类型" clearable>
          <el-option
            v-for="dict in dict.type.t_categories"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="部件ID" prop="componentId">
        <el-input
          v-model="queryParams.componentId"
          placeholder="请输入部件ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="计划所属年份" prop="year">
        <el-input
          v-model="queryParams.year"
          placeholder="请输入计划所属年份"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="计划维护日期" prop="scheduledDate">
        <el-date-picker clearable
          v-model="queryParams.scheduledDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择计划维护日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="计划状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择计划状态" clearable>
          <el-option
            v-for="dict in dict.type.dev_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="维护人员ID" prop="assignedPersonnelId">
        <el-input
          v-model="queryParams.assignedPersonnelId"
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
          v-hasPermi="['system:plan:add']"
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
          v-hasPermi="['system:plan:edit']"
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
          v-hasPermi="['system:plan:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:plan:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <!-- 主表 -->
    <el-table v-loading="loading" :data="planList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="计划主键ID" align="center" prop="planId" />
      <el-table-column label="维护设备类型" align="center" prop="targetType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.t_categories" :value="scope.row.targetType"/>
        </template>
      </el-table-column>
      <el-table-column label="部件ID" align="center" prop="componentId" />
      <el-table-column label="计划所属年份" align="center" prop="year" />
      <el-table-column label="计划维护日期" align="center" prop="scheduledDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.scheduledDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="计划状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.dev_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="维护人员ID" align="center" prop="assignedPersonnelId" />
      <el-table-column label="计划备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:plan:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:plan:remove']"
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

    <!-- 添加或修改年度作业计划对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <!-- 主表表单项 -->
        <el-row>
          <el-col :span="12">
            <el-form-item label="维护设备类型" prop="targetType">
              <el-select v-model="form.targetType" placeholder="请选择维护设备类型">
                <el-option
                  v-for="dict in dict.type.t_categories"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="部件ID" prop="componentId">
              <el-input v-model="form.componentId" placeholder="请输入部件ID" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="计划所属年份" prop="year">
              <el-input v-model="form.year" placeholder="请输入计划所属年份" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="计划维护日期" prop="scheduledDate">
              <el-date-picker clearable
                v-model="form.scheduledDate"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="请选择计划维护日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="计划状态" prop="status">
              <el-select v-model="form.status" placeholder="请选择计划状态">
                <el-option
                  v-for="dict in dict.type.dev_status"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="维护人员ID" prop="assignedPersonnelId">
              <el-input v-model="form.assignedPersonnelId" placeholder="请输入维护人员ID" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="计划备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>

        <!-- 子表编辑区域 -->
        <el-tabs v-model="activeName">
          <el-tab-pane label="维护作业完成情况" name="completionDetails">
            <el-table :data="form.devWorkCompletionDetailsList" ref="devWorkCompletionDetailsTable" @selection-change="handleDevWorkCompletionDetailsSelectionChange" :row-class-name="rowClassName">
              <el-table-column type="selection" width="50" align="center" />
              <el-table-column label="序号" align="center" prop="index" width="50"/>
              <el-table-column label="完成日期" prop="completionDate" width="240">
                <template slot-scope="scope">
                  <el-date-picker clearable v-model="scope.row.completionDate" type="datetime" value-format="yyyy-MM-dd HH:mm:ss" placeholder="请选择完成日期" />
                </template>
              </el-table-column>
              <el-table-column label="故障描述" prop="faultDescription">
                <template slot-scope="scope">
                    <el-input v-model="scope.row.faultDescription" placeholder="请输入故障描述" />
                </template>
              </el-table-column>
              <el-table-column label="采取措施" prop="actionsTaken">
                <template slot-scope="scope">
                    <el-input v-model="scope.row.actionsTaken" placeholder="请输入采取措施" />
                </template>
              </el-table-column>
              <el-table-column label="操作" align="center" width="100">
                  <template slot-scope="scope">
                      <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDeleteDevWorkCompletionDetails(scope.$index)">删除</el-button>
                  </template>
              </el-table-column>
            </el-table>
            <div style="margin-top: 10px">
              <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAddDevWorkCompletionDetails">添加行</el-button>
            </div>
          </el-tab-pane>

          <el-tab-pane label="设备归档历史" name="archivedComponents">
            <el-table :data="form.devArchivedComponentsList" ref="devArchivedComponentsTable" @selection-change="handleDevArchivedComponentsSelectionChange" :row-class-name="rowClassName">
              <el-table-column type="selection" width="50" align="center" />
              <el-table-column label="序号" align="center" prop="index" width="50"/>
              <el-table-column label="归档原因" prop="reasonForArchival">
                <template slot-scope="scope">
                  <el-input v-model="scope.row.reasonForArchival" placeholder="请输入归档原因" />
                </template>
              </el-table-column>
              <el-table-column label="归档日期" prop="archiveDate" width="240">
                <template slot-scope="scope">
                  <el-date-picker clearable v-model="scope.row.archiveDate" type="datetime" value-format="yyyy-MM-dd HH:mm:ss" placeholder="请选择归档日期" />
                </template>
              </el-table-column>
              <el-table-column label="操作" align="center" width="100">
                <template slot-scope="scope">
                  <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDeleteDevArchivedComponents(scope.$index)">删除</el-button>
                </template>
              </el-table-column>
            </el-table>
            <div style="margin-top: 10px">
              <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAddDevArchivedComponents">添加行</el-button>
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
import { listPlan, getPlan, delPlan, addPlan, updatePlan } from "@/api/system/plan";

export default {
  name: "Plan",
  dicts: ['dev_status', 't_categories'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 年度作业计划表格数据
      planList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 弹出层中Tab的激活页
      activeName: 'completionDetails',
      // 子表选中数据
      checkedDevWorkCompletionDetails: [],
      checkedDevArchivedComponents: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        targetType: null,
        componentId: null,
        year: null,
        scheduledDate: null,
        status: null,
        assignedPersonnelId: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        componentId: [
          { required: true, message: "部件ID不能为空", trigger: "blur" }
        ],
        year: [
          { required: true, message: "计划所属年份不能为空", trigger: "blur" }
        ],
        scheduledDate: [
          { required: true, message: "计划维护日期不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询年度作业计划列表 */
    getList() {
      this.loading = true;
      listPlan(this.queryParams).then(response => {
        this.planList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        planId: null,
        targetType: null,
        componentId: null,
        year: null,
        scheduledDate: null,
        status: "待执行",
        assignedPersonnelId: null,
        remark: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        // 统一在form对象中初始化子表列表
        devWorkCompletionDetailsList: [],
        devArchivedComponentsList: []
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.planId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加年度作业计划";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const planId = row.planId || this.ids[0];
      getPlan(planId).then(response => {
        this.form = response.data;
        // 确保子表列表不是null，如果是则置为空数组
        if (!this.form.devWorkCompletionDetailsList) {
            this.form.devWorkCompletionDetailsList = [];
        }
        if (!this.form.devArchivedComponentsList) {
            this.form.devArchivedComponentsList = [];
        }
        this.open = true;
        this.title = "修改年度作业计划";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          // 提交时，form对象已包含所有子表数据，无需额外赋值
          if (this.form.planId != null) {
            updatePlan(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addPlan(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const planIds = row.planId || this.ids;
      this.$modal.confirm('是否确认删除年度作业计划编号为"' + planIds + '"的数据项？').then(function() {
        return delPlan(planIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/plan/export', {
        ...this.queryParams
      }, `plan_${new Date().getTime()}.xlsx`);
    },
    /** 为子表行添加序号 */
    rowClassName({ row, rowIndex }) {
      row.index = rowIndex + 1;
    },

    // --- 子表1: 维护作业完成情况详情 操作 ---
    /** 添加行按钮 */
    handleAddDevWorkCompletionDetails() {
      if (!this.form.devWorkCompletionDetailsList) {
        this.form.devWorkCompletionDetailsList = [];
      }
      this.form.devWorkCompletionDetailsList.push({
        completionDate: null,
        faultDescription: "",
        actionsTaken: "",
        partsReplaced: "",
        reportUrl: "",
        remark: ""
      });
    },
    /** 删除行按钮 */
    handleDeleteDevWorkCompletionDetails(index) {
        this.form.devWorkCompletionDetailsList.splice(index, 1);
    },
    /** 复选框选中数据 */
    handleDevWorkCompletionDetailsSelectionChange(selection) {
      this.checkedDevWorkCompletionDetails = selection;
    },

    // --- 子表2: 设备归档历史 操作 ---
    /** 添加行按钮 */
    handleAddDevArchivedComponents() {
      if (!this.form.devArchivedComponentsList) {
        this.form.devArchivedComponentsList = [];
      }
      this.form.devArchivedComponentsList.push({
        reasonForArchival: "",
        archiveDate: null,
      });
    },
    /** 删除行按钮 */
    handleDeleteDevArchivedComponents(index) {
      this.form.devArchivedComponentsList.splice(index, 1);
    },
    /** 复选框选中数据 */
    handleDevArchivedComponentsSelectionChange(selection) {
      this.checkedDevArchivedComponents = selection;
    }
  }
};
</script>
