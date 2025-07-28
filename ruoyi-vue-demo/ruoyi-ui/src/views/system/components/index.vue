<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="所属设备ID" prop="masterEquipmentId">
        <el-input
          v-model="queryParams.masterEquipmentId"
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
      <el-form-item label="原部件ID" prop="componentId">
        <el-input
          v-model="queryParams.componentId"
          placeholder="请输入原部件ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="投入时间" prop="installDate">
        <el-date-picker clearable
          v-model="queryParams.installDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择投入时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="最大年限" prop="maxLifespanDate">
        <el-date-picker clearable
          v-model="queryParams.maxLifespanDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择最大年限">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="质保时间" prop="warrantyEndDate">
        <el-date-picker clearable
          v-model="queryParams.warrantyEndDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择质保时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="归档时的维保商" prop="vendorName">
        <el-input
          v-model="queryParams.vendorName"
          placeholder="请输入归档时的维保商"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="归档日期" prop="archiveDate">
        <el-date-picker clearable
          v-model="queryParams.archiveDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择归档日期">
        </el-date-picker>
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
          v-hasPermi="['system:components:add']"
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
          v-hasPermi="['system:components:edit']"
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
          v-hasPermi="['system:components:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:components:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="componentsList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="历史记录主键ID" align="center" prop="id" />
      <el-table-column label="所属设备ID" align="center" prop="masterEquipmentId" />
      <el-table-column label="部件名称" align="center" prop="componentName" />
      <el-table-column label="原部件ID" align="center" prop="componentId" />
      <el-table-column label="投入时间" align="center" prop="installDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.installDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="最大年限" align="center" prop="maxLifespanDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.maxLifespanDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="质保时间" align="center" prop="warrantyEndDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.warrantyEndDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="维修规范" align="center" prop="maintenanceRules" />
      <el-table-column label="归档时的维保商" align="center" prop="vendorName" />
      <el-table-column label="归档日期" align="center" prop="archiveDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.archiveDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="归档原因" align="center" prop="reasonForArchival" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:components:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:components:remove']"
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

    <!-- 添加或修改设备归档历史对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="所属设备ID" prop="masterEquipmentId">
          <el-input v-model="form.masterEquipmentId" placeholder="请输入所属设备ID" />
        </el-form-item>
        <el-form-item label="部件名称" prop="componentName">
          <el-input v-model="form.componentName" placeholder="请输入部件名称" />
        </el-form-item>
        <el-form-item label="原部件ID" prop="componentId">
          <el-input v-model="form.componentId" placeholder="请输入原部件ID" />
        </el-form-item>
        <el-form-item label="投入时间" prop="installDate">
          <el-date-picker clearable
            v-model="form.installDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择投入时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="最大年限" prop="maxLifespanDate">
          <el-date-picker clearable
            v-model="form.maxLifespanDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择最大年限">
          </el-date-picker>
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
        <el-form-item label="归档时的维保商" prop="vendorName">
          <el-input v-model="form.vendorName" placeholder="请输入归档时的维保商" />
        </el-form-item>
        <el-form-item label="归档日期" prop="archiveDate">
          <el-date-picker clearable
            v-model="form.archiveDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择归档日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="归档原因" prop="reasonForArchival">
          <el-input v-model="form.reasonForArchival" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listComponents, getComponents, delComponents, addComponents, updateComponents } from "@/api/system/components"

export default {
  name: "Components",
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
      // 设备归档历史表格数据
      componentsList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        masterEquipmentId: null,
        componentName: null,
        componentId: null,
        installDate: null,
        maxLifespanDate: null,
        warrantyEndDate: null,
        maintenanceRules: null,
        vendorName: null,
        archiveDate: null,
        reasonForArchival: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        masterEquipmentId: [
          { required: true, message: "所属设备ID不能为空", trigger: "blur" }
        ],
        componentName: [
          { required: true, message: "部件名称不能为空", trigger: "blur" }
        ],
        componentId: [
          { required: true, message: "原部件ID不能为空", trigger: "blur" }
        ],
        archiveDate: [
          { required: true, message: "归档日期不能为空", trigger: "blur" }
        ],
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询设备归档历史列表 */
    getList() {
      this.loading = true
      listComponents(this.queryParams).then(response => {
        this.componentsList = response.rows
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
        masterEquipmentId: null,
        componentName: null,
        componentId: null,
        installDate: null,
        maxLifespanDate: null,
        warrantyEndDate: null,
        maintenanceRules: null,
        vendorName: null,
        archiveDate: null,
        reasonForArchival: null,
        remark: null,
        createBy: null,
        createTime: null
      }
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
      this.title = "添加设备归档历史"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getComponents(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改设备归档历史"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateComponents(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addComponents(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除设备归档历史编号为"' + ids + '"的数据项？').then(function() {
        return delComponents(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/components/export', {
        ...this.queryParams
      }, `components_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
