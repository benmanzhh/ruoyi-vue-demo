<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="设备大类" prop="category">
        <el-select v-model="queryParams.category" placeholder="请选择设备大类" clearable>
          <el-option
            v-for="dict in dict.type.t_categories"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="设备名称" prop="equipmentName">
        <el-input
          v-model="queryParams.equipmentName"
          placeholder="请输入设备名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="设备ID" prop="equipmentId">
        <el-input
          v-model="queryParams.equipmentId"
          placeholder="请输入设备ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="楼号" prop="buildingNo">
        <el-select v-model="queryParams.buildingNo" placeholder="请选择楼号" clearable>
          <el-option
            v-for="dict in dict.type.t_buildings"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="机房位置" prop="location">
        <el-select v-model="queryParams.location" placeholder="请选择机房位置" clearable>
          <el-option
            v-for="dict in dict.type.t_locations"
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
      <el-form-item label="使用年限" prop="lifespanYears">
        <el-input
          v-model="queryParams.lifespanYears"
          placeholder="请输入使用年限"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="最大年限" prop="maxLifespanDate">
        <el-date-picker clearable
          v-model="queryParams.maxLifespanDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择最大年限">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="质保年限" prop="warrantyYears">
        <el-input
          v-model="queryParams.warrantyYears"
          placeholder="请输入质保年限"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="质保时间" prop="warrantyEndDate">
        <el-date-picker clearable
          v-model="queryParams.warrantyEndDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择质保时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="下一次计划维护时间" prop="nextMaintenanceDate">
        <el-date-picker clearable
          v-model="queryParams.nextMaintenanceDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择下一次计划维护时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="维护人员ID" prop="maintainerId">
        <el-input
          v-model="queryParams.maintainerId"
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

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:master:add']"
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
          v-hasPermi="['system:master:edit']"
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
          v-hasPermi="['system:master:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:master:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="masterList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键ID" align="center" prop="id" />
      <el-table-column label="设备大类" align="center" prop="category">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.t_categories" :value="scope.row.category"/>
        </template>
      </el-table-column>
      <el-table-column label="设备名称" align="center" prop="equipmentName" />
      <el-table-column label="设备ID" align="center" prop="equipmentId" />
      <el-table-column label="楼号" align="center" prop="buildingNo">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.t_buildings" :value="scope.row.buildingNo"/>
        </template>
      </el-table-column>
      <el-table-column label="机房位置" align="center" prop="location">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.t_locations" :value="scope.row.location"/>
        </template>
      </el-table-column>
      <el-table-column label="投入时间" align="center" prop="installDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.installDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="使用年限" align="center" prop="lifespanYears" />
      <el-table-column label="最大年限" align="center" prop="maxLifespanDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.maxLifespanDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="质保年限" align="center" prop="warrantyYears" />
      <el-table-column label="质保时间" align="center" prop="warrantyEndDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.warrantyEndDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="维修规范" align="center" prop="maintenanceRules" />
      <el-table-column label="维修规范文件上传" align="center" prop="rulesFileUrl" />
      <el-table-column label="下一次计划维护时间" align="center" prop="nextMaintenanceDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.nextMaintenanceDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="维护人员ID" align="center" prop="maintainerId" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:master:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:master:remove']"
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

    <!-- 添加或修改设备主档对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="设备大类" prop="category">
          <el-select v-model="form.category" placeholder="请选择设备大类">
            <el-option
              v-for="dict in dict.type.t_categories"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="设备名称" prop="equipmentName">
          <el-input v-model="form.equipmentName" placeholder="请输入设备名称" />
        </el-form-item>
        <el-form-item label="设备ID" prop="equipmentId">
          <el-input v-model="form.equipmentId" placeholder="请输入设备ID" />
        </el-form-item>
        <el-form-item label="楼号" prop="buildingNo">
          <el-select v-model="form.buildingNo" placeholder="请选择楼号">
            <el-option
              v-for="dict in dict.type.t_buildings"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="机房位置" prop="location">
          <el-select v-model="form.location" placeholder="请选择机房位置">
            <el-option
              v-for="dict in dict.type.t_locations"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="投入时间" prop="installDate">
          <el-date-picker clearable
            v-model="form.installDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择投入时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="使用年限" prop="lifespanYears">
          <el-input v-model="form.lifespanYears" placeholder="请输入使用年限" />
        </el-form-item>
        <el-form-item label="最大年限" prop="maxLifespanDate">
          <el-date-picker clearable
            v-model="form.maxLifespanDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择最大年限">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="质保年限" prop="warrantyYears">
          <el-input v-model="form.warrantyYears" placeholder="请输入质保年限" />
        </el-form-item>
        <el-form-item label="质保时间" prop="warrantyEndDate">
          <el-date-picker clearable
            v-model="form.warrantyEndDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择质保时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="维修规范" prop="maintenanceRules">
          <el-input v-model="form.maintenanceRules" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="维修规范文件上传" prop="rulesFileUrl">
          <file-upload v-model="form.rulesFileUrl"/>
        </el-form-item>
        <el-form-item label="下一次计划维护时间" prop="nextMaintenanceDate">
          <el-date-picker clearable
            v-model="form.nextMaintenanceDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择下一次计划维护时间">
          </el-date-picker>
        </el-form-item>

         <!-- (新增) 搜索项 -->
              <el-form-item label="实际维护时间" prop="actualMaintenanceDate">
                <el-date-picker clearable
                  v-model="queryParams.actualMaintenanceDate"
                  type="date"
                  value-format="yyyy-MM-dd"
                  placeholder="请选择">
                </el-date-picker>
              </el-form-item>

        <el-form-item label="维护人员ID" prop="maintainerId">
          <el-input v-model="form.maintainerId" placeholder="请输入维护人员ID" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-divider content-position="center">设备部件信息</el-divider>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAddDevEquipmentComponents">添加</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" icon="el-icon-delete" size="mini" @click="handleDeleteDevEquipmentComponents">删除</el-button>
          </el-col>
        </el-row>
        <el-table :data="devEquipmentComponentsList" :row-class-name="rowDevEquipmentComponentsIndex" @selection-change="handleDevEquipmentComponentsSelectionChange" ref="devEquipmentComponents">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="序号" align="center" prop="index" width="50"/>
          <el-table-column label="部件名称" prop="componentName" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.componentName" placeholder="请输入部件名称" />
            </template>
          </el-table-column>
          <el-table-column label="部件ID" prop="componentId" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.componentId" placeholder="请输入部件ID" />
            </template>
          </el-table-column>
          <el-table-column label="部件状态" prop="status" width="150">
            <template slot-scope="scope">
              <el-select v-model="scope.row.status" placeholder="请选择部件状态">
                <el-option
                  v-for="dict in dict.type.dev_status"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="投入时间" prop="installDate" width="240">
            <template slot-scope="scope">
              <el-date-picker clearable v-model="scope.row.installDate" type="date" value-format="yyyy-MM-dd" placeholder="请选择投入时间" />
            </template>
          </el-table-column>
          <el-table-column label="使用年限" prop="lifespanYears" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.lifespanYears" placeholder="请输入使用年限" />
            </template>
          </el-table-column>
          <el-table-column label="最大年限" prop="maxLifespanDate" width="240">
            <template slot-scope="scope">
              <el-date-picker clearable v-model="scope.row.maxLifespanDate" type="date" value-format="yyyy-MM-dd" placeholder="请选择最大年限" />
            </template>
          </el-table-column>
          <el-table-column label="质保年限" prop="warrantyYears" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.warrantyYears" placeholder="请输入质保年限" />
            </template>
          </el-table-column>
          <el-table-column label="质保时间" prop="warrantyEndDate" width="240">
            <template slot-scope="scope">
              <el-date-picker clearable v-model="scope.row.warrantyEndDate" type="date" value-format="yyyy-MM-dd" placeholder="请选择质保时间" />
            </template>
          </el-table-column>
          <el-table-column label="下一次计划维护时间" prop="nextMaintenanceDate" width="240">
            <template slot-scope="scope">
              <el-date-picker clearable v-model="scope.row.nextMaintenanceDate" type="date" value-format="yyyy-MM-dd" placeholder="请选择下一次计划维护时间" />
            </template>
          </el-table-column>

          <!-- (新增) 表格列 -->
                <el-table-column label="实际维护时间" align="center" prop="actualMaintenanceDate" width="180">
                  <template slot-scope="scope">
                    <span>{{ parseTime(scope.row.actualMaintenanceDate, '{y}-{m}-{d}') }}</span>
                  </template>
                </el-table-column>

          <el-table-column label="维护人员(员工id)" prop="personnelId" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.personnelId" placeholder="请输入维护人员(员工id)" />
            </template>
          </el-table-column>
        </el-table>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listMaster, getMaster, delMaster, addMaster, updateMaster } from "@/api/system/master"

export default {
  name: "Master",
  dicts: ['t_locations', 'dev_status', 't_buildings', 't_categories'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 子表选中数据
      checkedDevEquipmentComponents: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 设备主档表格数据
      masterList: [],
      // 设备部件表格数据
      devEquipmentComponentsList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        category: null,
        equipmentName: null,
        equipmentId: null,
        buildingNo: null,
        location: null,
        installDate: null,
        lifespanYears: null,
        maxLifespanDate: null,
        warrantyYears: null,
        warrantyEndDate: null,
        maintenanceRules: null,
        rulesFileUrl: null,
        nextMaintenanceDate: null,
        actualMaintenanceDate: null,
        maintainerId: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        equipmentName: [
          { required: true, message: "设备名称不能为空", trigger: "blur" }
        ],
        equipmentId: [
          { required: true, message: "设备ID不能为空", trigger: "blur" }
        ],
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询设备主档列表 */
    getList() {
      this.loading = true
      listMaster(this.queryParams).then(response => {
        this.masterList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        category: null,
        equipmentName: null,
        equipmentId: null,
        buildingNo: null,
        location: null,
        installDate: null,
        lifespanYears: null,
        maxLifespanDate: null,
        warrantyYears: null,
        warrantyEndDate: null,
        maintenanceRules: null,
        rulesFileUrl: null,
        nextMaintenanceDate: null,
        maintainerId: null,
        remark: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null
      }
      this.devEquipmentComponentsList = []
      this.resetForm("form")
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm")
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加设备主档"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getMaster(id).then(response => {
        this.form = response.data
        this.devEquipmentComponentsList = response.data.devEquipmentComponentsList
        this.open = true
        this.title = "修改设备主档"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.devEquipmentComponentsList = this.devEquipmentComponentsList
          if (this.form.id != null) {
            updateMaster(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addMaster(this.form).then(response => {
              this.$modal.msgSuccess("新增成功")
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$modal.confirm('是否确认删除设备主档编号为"' + ids + '"的数据项？').then(function() {
        return delMaster(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
	/** 设备部件序号 */
    rowDevEquipmentComponentsIndex({ row, rowIndex }) {
      row.index = rowIndex + 1
    },
    /** 设备部件添加按钮操作 */
    handleAddDevEquipmentComponents() {
      let obj = {}
      obj.componentName = ""
      obj.componentId = ""
      obj.status = ""
      obj.installDate = ""
      obj.lifespanYears = ""
      obj.maxLifespanDate = ""
      obj.warrantyYears = ""
      obj.warrantyEndDate = ""
      obj.maintenanceRules = ""
      obj.rulesFileUrl = ""
      obj.nextMaintenanceDate = ""
      obj.personnelId = ""
      obj.remark = ""
      this.devEquipmentComponentsList.push(obj)
    },
    /** 设备部件删除按钮操作 */
    handleDeleteDevEquipmentComponents() {
      if (this.checkedDevEquipmentComponents.length == 0) {
        this.$modal.msgError("请先选择要删除的设备部件数据")
      } else {
        const devEquipmentComponentsList = this.devEquipmentComponentsList
        const checkedDevEquipmentComponents = this.checkedDevEquipmentComponents
        this.devEquipmentComponentsList = devEquipmentComponentsList.filter(function(item) {
          return checkedDevEquipmentComponents.indexOf(item.index) == -1
        })
      }
    },
    /** 复选框选中数据 */
    handleDevEquipmentComponentsSelectionChange(selection) {
      this.checkedDevEquipmentComponents = selection.map(item => item.index)
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/master/export', {
        ...this.queryParams
      }, `master_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
