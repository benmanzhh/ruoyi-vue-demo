<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="员工ID" prop="personnelId">
        <el-input
          v-model="queryParams.personnelId"
          placeholder="请输入员工ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="姓名" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入姓名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="联系方式" prop="contactInfo">
        <el-input
          v-model="queryParams.contactInfo"
          placeholder="请输入联系方式"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <!-- (核心修正) 将人员属性搜索框改为下拉选择 -->
      <el-form-item label="人员属性" prop="personnelType">
        <el-select v-model="queryParams.personnelType" placeholder="请选择人员属性" clearable>
          <el-option
            v-for="dict in dict.type.t_personnel"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
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
          v-hasPermi="['system:personnel:add']"
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
          v-hasPermi="['system:personnel:edit']"
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
          v-hasPermi="['system:personnel:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:personnel:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="personnelList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="人员主键ID" align="center" prop="id" />
      <el-table-column label="员工ID" align="center" prop="personnelId" />
      <el-table-column label="姓名" align="center" prop="name" />
      <el-table-column label="员工联系方式" align="center" prop="contactInfo" />
      <!-- (核心修正) 使用 dict-tag 显示人员属性标签 -->
      <el-table-column label="人员属性" align="center" prop="personnelType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.t_personnel" :value="scope.row.personnelType"/>
        </template>
      </el-table-column>
      <el-table-column label="证书到期时间" align="center" prop="certificateExpiryDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.certificateExpiryDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="技能考核打分" align="center" prop="skillScore" />
      <el-table-column label="所属供应商ID" align="center" prop="vendorId" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:personnel:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:personnel:remove']"
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

    <!-- 添加或修改维护人员信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="员工ID" prop="personnelId">
              <el-input v-model="form.personnelId" placeholder="请输入员工ID" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="姓名" prop="name">
              <el-input v-model="form.name" placeholder="请输入姓名" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="员工联系方式" prop="contactInfo">
              <el-input v-model="form.contactInfo" placeholder="请输入联系方式" />
            </el-form-item>
          </el-col>
           <el-col :span="12">
            <!-- (核心修正) 将人员属性输入框改为下拉选择 -->
            <el-form-item label="人员属性" prop="personnelType">
              <el-select v-model="form.personnelType" placeholder="请选择人员属性">
                <el-option
                  v-for="dict in dict.type.t_personnel"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="证书要求" prop="certificateInfo">
          <el-input v-model="form.certificateInfo" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="证书拍照" prop="certificatePhotoUrl">
          <file-upload v-model="form.certificatePhotoUrl"/>
        </el-form-item>
        <el-form-item label="证书到期时间" prop="certificateExpiryDate">
          <el-date-picker clearable
            v-model="form.certificateExpiryDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择证书到期时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="技能考核打分" prop="skillScore">
          <el-input-number v-model="form.skillScore" :precision="2" :step="0.1" placeholder="请输入技能考核打分情况" />
        </el-form-item>
        <el-form-item label="维护人员权限" prop="permissions">
          <el-input v-model="form.permissions" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="所属供应商ID" prop="vendorId">
          <el-input v-model="form.vendorId" placeholder="请输入所属供应商ID" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-divider content-position="center">供应商信息</el-divider>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAddDevVendors">添加</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" icon="el-icon-delete" size="mini" @click="handleDeleteDevVendors">删除</el-button>
          </el-col>
        </el-row>
        <el-table :data="devVendorsList" :row-class-name="rowDevVendorsIndex" @selection-change="handleDevVendorsSelectionChange" ref="devVendors">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="序号" align="center" prop="index" width="50"/>
          <el-table-column label="供应商名称" prop="vendorName" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.vendorName" placeholder="请输入供应商名称" />
            </template>
          </el-table-column>
          <el-table-column label="供应商联系方式" prop="contactInfo" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.contactInfo" placeholder="请输入供应商联系方式" />
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
import { listPersonnel, getPersonnel, delPersonnel, addPersonnel, updatePersonnel } from "@/api/system/personnel";

export default {
  name: "Personnel",
  // (核心修正) 引入新的字典类型
  dicts: ['t_personnel'],
  data() {
    return {
      loading: true,
      ids: [],
      checkedDevVendors: [],
      single: true,
      multiple: true,
      showSearch: true,
      total: 0,
      personnelList: [],
      devVendorsList: [],
      title: "",
      open: false,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        personnelId: null,
        name: null,
        contactInfo: null,
        personnelType: null, // (新增)
        certificateExpiryDate: null,
        skillScore: null,
        vendorId: null,
      },
      form: {},
      rules: {
        personnelId: [
          { required: true, message: "员工ID不能为空", trigger: "blur" }
        ],
        name: [
          { required: true, message: "姓名不能为空", trigger: "blur" }
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
      listPersonnel(this.queryParams).then(response => {
        this.personnelList = response.rows;
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
        personnelId: null,
        name: null,
        contactInfo: null,
        personnelType: null,
        certificateInfo: null,
        certificatePhotoUrl: null,
        certificateExpiryDate: null,
        skillScore: null,
        permissions: null,
        vendorId: null,
        remark: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        devVendorsList: []
      };
      this.devVendorsList = [];
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
      this.single = selection.length!==1;
      this.multiple = !selection.length;
    },
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加维护人员信息";
    },
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids[0];
      getPersonnel(id).then(response => {
        this.form = response.data;
        this.devVendorsList = response.data.devVendorsList || [];
        this.open = true;
        this.title = "修改维护人员信息";
      });
    },
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.devVendorsList = this.devVendorsList;
          if (this.form.id != null) {
            updatePersonnel(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addPersonnel(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除维护人员信息编号为"' + ids + '"的数据项？').then(function() {
        return delPersonnel(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    rowDevVendorsIndex({ row, rowIndex }) {
      row.index = rowIndex + 1;
    },
    handleAddDevVendors() {
      let obj = {};
      obj.vendorName = "";
      obj.contactInfo = "";
      obj.remark = "";
      this.devVendorsList.push(obj);
    },
    handleDeleteDevVendors() {
      if (this.checkedDevVendors.length == 0) {
        this.$modal.msgError("请先选择要删除的供应商信息数据");
      } else {
        const devVendorsList = this.devVendorsList;
        const checkedDevVendors = this.checkedDevVendors;
        this.devVendorsList = devVendorsList.filter(function(item) {
          return checkedDevVendors.indexOf(item.index) == -1;
        });
      }
    },
    handleDevVendorsSelectionChange(selection) {
      this.checkedDevVendors = selection.map(item => item.index);
    },
    handleExport() {
      this.download('system/personnel/export', {
        ...this.queryParams
      }, `personnel_${new Date().getTime()}.xlsx`);
    }
  }
};
</script>
