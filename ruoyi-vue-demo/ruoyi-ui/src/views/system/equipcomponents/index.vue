<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
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
      <el-form-item label="维护人员(员工id)" prop="personnelId">
        <el-input
          v-model="queryParams.personnelId"
          placeholder="请输入维护人员(员工id)"
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

    <el-table v-loading="loading" :data="equipcomponentsList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="部件主键ID" align="center" prop="id" />
      <el-table-column label="所属设备ID" align="center" prop="equipmentId" />
      <el-table-column label="部件名称" align="center" prop="componentName" />
      <el-table-column label="部件ID" align="center" prop="componentId" />
      <el-table-column label="部件状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.dev_status" :value="scope.row.status"/>
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
      <el-table-column label="维护人员(员工id)" align="center" prop="personnelId" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
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
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="所属设备ID" prop="equipmentId">
          <el-input v-model="form.equipmentId" placeholder="请输入所属设备ID" />
        </el-form-item>
        <el-form-item label="部件名称" prop="componentName">
          <el-input v-model="form.componentName" placeholder="请输入部件名称" />
        </el-form-item>
        <el-form-item label="部件ID" prop="componentId">
          <el-input v-model="form.componentId" placeholder="请输入部件ID" />
        </el-form-item>
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
        <el-form-item label="维护人员(员工id)" prop="personnelId">
          <el-input v-model="form.personnelId" placeholder="请输入维护人员(员工id)" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-divider content-position="center">维护人员信息信息</el-divider>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAddDevPersonnel">添加</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" icon="el-icon-delete" size="mini" @click="handleDeleteDevPersonnel">删除</el-button>
          </el-col>
        </el-row>
        <el-table :data="devPersonnelList" :row-class-name="rowDevPersonnelIndex" @selection-change="handleDevPersonnelSelectionChange" ref="devPersonnel">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="序号" align="center" prop="index" width="50"/>
          <el-table-column label="姓名" prop="name" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.name" placeholder="请输入姓名" />
            </template>
          </el-table-column>
          <el-table-column label="人员属性" prop="personnelType" width="150">
            <template slot-scope="scope">
              <el-select v-model="scope.row.personnelType" placeholder="请选择人员属性">
                <el-option label="请选择字典生成" value="" />
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="证书到期时间" prop="certificateExpiryDate" width="240">
            <template slot-scope="scope">
              <el-date-picker clearable v-model="scope.row.certificateExpiryDate" type="date" value-format="yyyy-MM-dd" placeholder="请选择证书到期时间" />
            </template>
          </el-table-column>
          <el-table-column label="技能考核打分情况" prop="skillScore" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.skillScore" placeholder="请输入技能考核打分情况" />
            </template>
          </el-table-column>
          <el-table-column label="所属供应商ID" prop="vendorId" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.vendorId" placeholder="请输入所属供应商ID" />
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
import { listEquipcomponents, getEquipcomponents, delEquipcomponents, addEquipcomponents, updateEquipcomponents } from "@/api/system/equipcomponents"

export default {
  name: "Equipcomponents",
  dicts: ['dev_status'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 子表选中数据
      checkedDevPersonnel: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 设备部件表格数据
      equipcomponentsList: [],
      // 维护人员信息表格数据
      devPersonnelList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        equipmentId: null,
        componentName: null,
        componentId: null,
        status: null,
        installDate: null,
        lifespanYears: null,
        maxLifespanDate: null,
        warrantyYears: null,
        warrantyEndDate: null,
        maintenanceRules: null,
        rulesFileUrl: null,
        nextMaintenanceDate: null,
        personnelId: null,
      },
      // 表单参数
      form: {},
      // 表单校验
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
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询设备部件列表 */
    getList() {
      this.loading = true
      listEquipcomponents(this.queryParams).then(response => {
        this.equipcomponentsList = response.rows
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
        equipmentId: null,
        componentName: null,
        componentId: null,
        status: null,
        installDate: null,
        lifespanYears: null,
        maxLifespanDate: null,
        warrantyYears: null,
        warrantyEndDate: null,
        maintenanceRules: null,
        rulesFileUrl: null,
        nextMaintenanceDate: null,
        personnelId: null,
        remark: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null
      }
      this.devPersonnelList = []
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
      this.title = "添加设备部件"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getEquipcomponents(id).then(response => {
        this.form = response.data
        this.devPersonnelList = response.data.devPersonnelList
        this.open = true
        this.title = "修改设备部件"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.devPersonnelList = this.devPersonnelList
          if (this.form.id != null) {
            updateEquipcomponents(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addEquipcomponents(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除设备部件编号为"' + ids + '"的数据项？').then(function() {
        return delEquipcomponents(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
	/** 维护人员信息序号 */
    rowDevPersonnelIndex({ row, rowIndex }) {
      row.index = rowIndex + 1
    },
    /** 维护人员信息添加按钮操作 */
    handleAddDevPersonnel() {
      let obj = {}
      obj.name = ""
      obj.personnelType = ""
      obj.certificateInfo = ""
      obj.certificatePhotoUrl = ""
      obj.certificateExpiryDate = ""
      obj.skillScore = ""
      obj.permissions = ""
      obj.vendorId = ""
      obj.remark = ""
      this.devPersonnelList.push(obj)
    },
    /** 维护人员信息删除按钮操作 */
    handleDeleteDevPersonnel() {
      if (this.checkedDevPersonnel.length == 0) {
        this.$modal.msgError("请先选择要删除的维护人员信息数据")
      } else {
        const devPersonnelList = this.devPersonnelList
        const checkedDevPersonnel = this.checkedDevPersonnel
        this.devPersonnelList = devPersonnelList.filter(function(item) {
          return checkedDevPersonnel.indexOf(item.index) == -1
        })
      }
    },
    /** 复选框选中数据 */
    handleDevPersonnelSelectionChange(selection) {
      this.checkedDevPersonnel = selection.map(item => item.index)
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/equipcomponents/export', {
        ...this.queryParams
      }, `equipcomponents_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
