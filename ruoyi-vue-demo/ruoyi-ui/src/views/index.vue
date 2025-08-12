<template>
  <div class="app-container home">
    <el-row :gutter="20">
      <el-col :sm="24" :lg="12" style="padding-left: 22px">
        <h2>idc设备维护全生命周期系统</h2>
        <p>
          具体内部表单包含对设备整体表单记录、设备部件表单记录、年度作业计划表记录、作业维护细节表、归档部件表、维护作业黑名单表、人员表、供应商表。
        </p>
        <p>
          <b>当前版本:</b> <span>v{{ version }}</span>
        </p>
        <p>
          <el-button
            type="primary"
            size="mini"
            icon="el-icon-cloudy"
            plain
            @click="goTarget('https://gitee.com/y_project/RuoYi-Vue')"
            >访问码云</el-button
          >
          <el-button
            size="mini"
            icon="el-icon-s-home"
            plain
            @click="goTarget('http://ruoyi.vip')"
            >访问主页</el-button
          >
        </p>
      </el-col>

      <el-col :sm="24" :lg="12" style="padding-left: 50px">
        <el-row>
          <el-col :span="12">
            <h2>表单分成</h2>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <h4>设备维护</h4>
            <ul>
              <li>设备主档表</li>
              <li>设备部件表</li>
            </ul>
          </el-col>
          <el-col :span="6">
            <h4>时间规划</h4>
            <ul>
              <li>年度作业计划表</li>
              <li>维护细节表</li>
              <li>归档部件表</li>
              <li>维护黑名单表</li>
            </ul>
          </el-col>
        </el-row>
      </el-col>
    </el-row>
    <el-divider />
    <el-row :gutter="20">
      <el-col :xs="24" :sm="24" :md="12" :lg="8">
        <el-card class="update-log">
          <div slot="header" class="clearfix">
            <span>联系信息</span>
          </div>
          <div class="body">
            <p><i class="el-icon-s-promotion"></i> 维护责任人：</p>
            <p><i class="el-icon-user-solid"></i> 联系电话：</p>
            <p><i class="el-icon-chat-dot-round"></i> 邮箱：</p>
          </div>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="12" :lg="8">
        <el-card class="update-log">
          <div slot="header" class="clearfix">
            <span>图标</span>
          </div>
          <div class="body">
                               <img
                                 src="@/assets/images/pay.png"
                                 alt="donate"
                                 width="100%"
                               />

                             </div>

        </el-card>
      </el-col>

      <!-- (优化) 统一的提醒框 -->
      <el-col :xs="24" :sm="24" :md="12" :lg="8">
        <el-card class="update-log">
          <div slot="header" class="clearfix">
            <span>生命周期与质保提醒</span>
            <el-button style="float: right; padding: 3px 0" type="text" @click="fetchCombinedAlerts" icon="el-icon-refresh"></el-button>
          </div>
          <div v-loading="loadingAlerts" class="body alert-body">
            <div v-if="!hasAlerts" class="no-alerts">
              <i class="el-icon-success"></i>
              <p>一切正常！暂无任何临期提醒。</p>
            </div>
            <div v-else>
              <!-- 使用年限提醒 -->
              <div class="alert-section" v-if="hasLifecycleAlerts">
                <h4>使用年限提醒</h4>
                <ul>
                  <li v-for="(item, index) in combinedAlerts.lifecycleMasterAlerts" :key="'life-master-' + index">
                    <div class="alert-item">
                      <i class="el-icon-warning-outline"></i>
                      <div class="alert-content">
                        <span><strong>设备:</strong> {{ item.equipmentId }} ({{ item.category }})</span>
                        <span class="location"><strong>位置:</strong> {{ item.location }}</span>
                        <span class="message">即将到达使用年限!</span>
                        <span class="maintainer"><strong>维护人ID:</strong> {{ item.maintainerId }}</span>
                      </div>
                    </div>
                  </li>
                   <li v-for="(item, index) in combinedAlerts.lifecycleComponentAlerts" :key="'life-comp-' + index">
                    <div class="alert-item">
                      <i class="el-icon-warning-outline"></i>
                      <div class="alert-content">
                        <span><strong>部件:</strong> {{ item.componentId }}</span>
                        <span class="location"><strong>所属设备:</strong> {{ item.equipmentId }}</span>
                        <span class="message">即将到达使用年限!</span>
                        <span class="maintainer"><strong>维护人ID:</strong> {{ item.personnelId }}</span>
                      </div>
                    </div>
                  </li>
                </ul>
              </div>
              <!-- 质保提醒 -->
              <div class="alert-section" v-if="hasWarrantyAlerts">
                <h4>质保提醒</h4>
                <ul>
                  <li v-for="(item, index) in combinedAlerts.warrantyMasterAlerts" :key="'warranty-master-' + index">
                    <div class="alert-item">
                      <i class="el-icon-warning-outline" style="color: #409EFF;"></i>
                      <div class="alert-content">
                        <span><strong>设备:</strong> {{ item.equipmentId }} ({{ item.category }})</span>
                        <span class="location"><strong>位置:</strong> {{ item.location }}</span>
                        <span class="message warranty-message">即将到达质保年限!</span>
                        <span class="maintainer"><strong>维护人ID:</strong> {{ item.maintainerId }}</span>
                      </div>
                    </div>
                  </li>
                   <li v-for="(item, index) in combinedAlerts.warrantyComponentAlerts" :key="'warranty-comp-' + index">
                    <div class="alert-item">
                      <i class="el-icon-warning-outline" style="color: #409EFF;"></i>
                      <div class="alert-content">
                        <span><strong>部件:</strong> {{ item.componentId }}</span>
                        <span class="location"><strong>所属设备:</strong> {{ item.equipmentId }}</span>
                        <span class="message warranty-message">即将到达质保年限!</span>
                        <span class="maintainer"><strong>维护人ID:</strong> {{ item.personnelId }}</span>
                      </div>
                    </div>
                  </li>
                </ul>
              </div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
// (优化) 引入统一的API方法
import { getCombinedAlerts } from "@/api/system/master"; // 请确保API路径正确

export default {
  name: "Index",
  data() {
    return {
      version: "0.0.1",
      // (优化) 用于存储所有提醒数据的统一对象
      combinedAlerts: {
        lifecycleMasterAlerts: [],
        lifecycleComponentAlerts: [],
        warrantyMasterAlerts: [],
        warrantyComponentAlerts: []
      },
      loadingAlerts: true
    };
  },
  computed: {
    // (优化) 计算属性，判断是否有任何类型的提醒
    hasAlerts() {
      return this.hasLifecycleAlerts || this.hasWarrantyAlerts;
    },
    hasLifecycleAlerts() {
      return (
        (this.combinedAlerts.lifecycleMasterAlerts && this.combinedAlerts.lifecycleMasterAlerts.length > 0) ||
        (this.combinedAlerts.lifecycleComponentAlerts && this.combinedAlerts.lifecycleComponentAlerts.length > 0)
      );
    },
    hasWarrantyAlerts() {
      return (
        (this.combinedAlerts.warrantyMasterAlerts && this.combinedAlerts.warrantyMasterAlerts.length > 0) ||
        (this.combinedAlerts.warrantyComponentAlerts && this.combinedAlerts.warrantyComponentAlerts.length > 0)
      );
    }
  },
  created() {
    this.fetchCombinedAlerts();
  },
  methods: {
    goTarget(href) {
      window.open(href, "_blank");
    },
    // (优化) 获取统一提醒数据的方法
    fetchCombinedAlerts() {
      this.loadingAlerts = true;
      getCombinedAlerts().then(response => {
        this.combinedAlerts = response.data;
        this.loadingAlerts = false;
      }).catch(() => {
        this.loadingAlerts = false;
      });
    }
  }
};
</script>

<style scoped lang="scss">
.home {
  // ... 其他样式保持不变 ...

  .alert-body {
    min-height: 200px;
    max-height: 300px;
    overflow-y: auto;

    .no-alerts {
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      height: 200px;
      color: #909399;
      i {
        font-size: 48px;
        color: #67C23A;
      }
      p {
        margin-top: 10px;
      }
    }
    .alert-section {
      margin-bottom: 15px;
      &:last-child {
        margin-bottom: 0;
      }
      h4 {
        font-size: 14px;
        margin: 0 0 8px 0;
        border-bottom: 1px solid #eee;
        padding-bottom: 5px;
      }
      ul {
        li {
          margin-bottom: 12px;
          .alert-item {
            display: flex;
            align-items: flex-start;
            i {
              color: #E6A23C;
              font-size: 18px;
              margin-right: 8px;
              margin-top: 2px;
            }
            .alert-content {
              display: flex;
              flex-direction: column;
              font-size: 13px;
              line-height: 1.5;
              .location, .maintainer {
                color: #909399;
                font-size: 12px;
              }
              .message {
                color: #F56C6C;
                font-weight: bold;
              }
              .warranty-message {
                  color: #409EFF;
              }
            }
          }
        }
      }
    }
  }
}
</style>
