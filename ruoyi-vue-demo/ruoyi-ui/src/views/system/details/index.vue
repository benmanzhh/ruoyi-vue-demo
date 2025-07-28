<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="关联的计划ID" prop="planId">
        <el-input
          v-model="queryParams.planId"
          placeholder="请输入关联的计划ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="完成日期" prop="completionDate">
        <el-date-picker clearable
          v-model="queryParams.completionDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择完成日期">
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
          v-hasPermi="['system:details:add']"
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
          v-hasPermi="['system:details:edit']"
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
          v-hasPermi="['system:details:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:details:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="detailsList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="完成情况ID" align="center" prop="id" />
      <el-table-column label="关联的计划ID" align="center" prop="planId" />
      <el-table-column label="完成日期" align="center" prop="completionDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.completionDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="故障现象描述" align="center" prop="faultDescription" />
      <el-table-column label="采取的维保措施" align="center" prop="actionsTaken" />
      <el-table-column label="更换备件信息" align="center" prop="partsReplaced" />
      <el-table-column label="维保报告链接" align="center" prop="reportUrl" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:details:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:details:remove']"
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

    <!-- 添加或修改维护作业完成情况详情对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="关联的计划ID" prop="planId">
          <el-input v-model="form.planId" placeholder="请输入关联的计划ID" />
        </el-form-item>
        <el-form-item label="完成日期" prop="completionDate">
          <el-date-picker clearable
            v-model="form.completionDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择完成日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="故障现象描述" prop="faultDescription">
          <el-input v-model="form.faultDescription" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="采取的维保措施" prop="actionsTaken">
          <el-input v-model="form.actionsTaken" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="更换备件信息" prop="partsReplaced">
          <el-input v-model="form.partsReplaced" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="维保报告链接" prop="reportUrl">
          <file-upload v-model="form.reportUrl"/>
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
import { listDetails, getDetails, delDetails, addDetails, updateDetails } from "@/api/system/details"

export default {
  name: "Details",
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
      // 维护作业完成情况详情表格数据
      detailsList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        planId: null,
        completionDate: null,
        faultDescription: null,
        actionsTaken: null,
        partsReplaced: null,
        reportUrl: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        planId: [
          { required: true, message: "关联的计划ID不能为空", trigger: "blur" }
        ],
        completionDate: [
          { required: true, message: "完成日期不能为空", trigger: "blur" }
        ],
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询维护作业完成情况详情列表 */
    getList() {
      this.loading = true
      listDetails(this.queryParams).then(response => {
        this.detailsList = response.rows
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
        planId: null,
        completionDate: null,
        faultDescription: null,
        actionsTaken: null,
        partsReplaced: null,
        reportUrl: null,
        remark: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null
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
      this.title = "添加维护作业完成情况详情"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getDetails(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改维护作业完成情况详情"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateDetails(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addDetails(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除维护作业完成情况详情编号为"' + ids + '"的数据项？').then(function() {
        return delDetails(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/details/export', {
        ...this.queryParams
      }, `details_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
