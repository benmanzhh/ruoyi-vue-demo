<template>
  <div class="app-container home">
    <el-row :gutter="20">
      <el-col :sm="24" :lg="12" style="padding-left: 20px">
        <h2>idc设备维护系统</h2>
        <p>
         讲解，内容待填充。。。。。。。。。。。。。。。。。。。。。。。。。。。。
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
            <h2>技术选型</h2>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <h4>后端技术</h4>
            <ul>
              <li>SpringBoot</li>
              <li>Spring Security</li>
              <li>JWT</li>
              <li>MyBatis</li>
              <li>Druid</li>
              <li>Fastjson</li>
              <li>...</li>
            </ul>
          </el-col>
          <el-col :span="6">
            <h4>前端技术</h4>
            <ul>
              <li>Vue</li>
              <li>Vuex</li>
              <li>Element-ui</li>
              <li>Axios</li>
              <li>Sass</li>
              <li>Quill</li>
              <li>...</li>
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
            <p>
              <i class="el-icon-s-promotion"></i> 官网：<el-link
                href="http://www.ruoyi.vip"
                target="_blank"
                >http://www.ruoyi.vip</el-link
              >
            </p>
            <p>
              <i class="el-icon-user-solid"></i> QQ群：<s> 满937441 </s> <s> 满887144332 </s>
              <s> 满180251782 </s> <s> 满104180207 </s> <s> 满186866453 </s> <s> 满201396349 </s>
              <s> 满228578329 </s> <a href="http://qm.qq.com/cgi-bin/qm/qr?_wv=1027&k=GsOo-OLz53J8y_9TPoO6XXSGNRTgbFxA&authKey=R7Uy%2Feq%2BZsoKNqHvRKhiXpypW7DAogoWapOawUGHokJSBIBIre2%2FoiAZeZBSLuBc&noverify=0&group_code=191164766" target="_blank">191164766</a>
            </p>
            <p>
              <i class="el-icon-chat-dot-round"></i> 微信：<a
                href="javascript:;"
                >/ *若依</a
              >
            </p>
            <p>
              <i class="el-icon-money"></i> 支付宝：<a
                href="javascript:;"
                class="支付宝信息"
                >/ *若依</a
              >
            </p>
          </div>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="12" :lg="8">
        <el-card class="update-log">
          <div slot="header" class="clearfix">
            <span>更新日志</span>
          </div>
          <el-collapse accordion>
            <el-collapse-item title="v3.9.0 - 2025-05-28">
              <ol>
                <li>优化菜单搜索查询页</li>
                <li>导航栏显示昵称&设置</li>
                <li>菜单管理新增路由名称</li>
                <li>添加底部版权信息&开关</li>
                <li>分配角色禁用不允许勾选</li>
                <li>Excel导入导出支持多图片</li>
                <li>添加页签图标显示开关功能</li>
                <li>上传组件新增拖动排序属性</li>
                <li>显隐列组件支持全选/全不选</li>
                <li>初始密码支持自定义修改策略</li>
                <li>账号密码支持自定义更新周期</li>
                <li>代码生成列表支持按时间排序</li>
                <li>支持富文本复制粘贴图片上传至url</li>
                <li>支持文件&图片组件自定义地址&参数</li>
                <li>升级tomcat到最新版本9.0.105</li>
                <li>升级oshi到最新版本6.8.1</li>
                <li>升级fastjson到最新版2.0.57</li>
                <li>升级commons.io到最新版本2.19.0</li>
                <li>package.json移除runjs依赖</li>
                <li>package.json移除eslint依赖</li>
                <li>package.json移除vue-meta依赖</li>
                <li>修复代码生成主子表校验必填失效问题</li>
                <li>优化前端树结构性能问题</li>
                <li>优化前端处理路由函数代码</li>
                <li>优化文件上传组件新增类型</li>
                <li>优化顶部菜单搜索栏为多层级显示</li>
                <li>优化文件&图片上传组件新增disabled属性</li>
                <li>优化空指针异常时无法获取错误信息问题</li>
                <li>优化定时任务字符包含多个括号导致数据错误</li>
                <li>优化登录&注册页表头使用VUE_APP_TITLE配置值</li>
                <li>优化导出Excel日期格式双击离开后与设定的格式不一致问题</li>
                <li>其他细节优化</li>
              </ol>
            </el-collapse-item>

            <el-collapse-item title="v1.1.0 - 2019-11-11">
              <ol>
                <li>新增在线用户管理</li>
                <li>新增按钮组功能实现（批量删除、导出、清空）</li>
                <li>新增查询条件重置按钮</li>
                <li>新增Swagger全局Token配置</li>
                <li>新增后端参数校验</li>
                <li>修复字典管理页面的日期查询异常</li>
                <li>修改时间函数命名防止冲突</li>
                <li>去除菜单上级校验，默认为顶级</li>
                <li>修复用户密码无法修改问题</li>
                <li>修复菜单类型为按钮时不显示权限标识</li>
                <li>其他细节优化</li>
              </ol>
            </el-collapse-item>
            <el-collapse-item title="v1.0.0 - 2019-10-08">
              <ol>
                <li>若依前后端分离系统正式发布</li>
              </ol>
            </el-collapse-item>
          </el-collapse>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="12" :lg="8">
        <el-card class="update-log">
          <div slot="header" class="clearfix">
            <span>捐赠支持</span>
          </div>
          <div class="body">
            <img
              src="@/assets/images/pay.png"
              alt="donate"
              width="100%"
            />
            <span style="display: inline-block; height: 30px; line-height: 30px"
              >你可以请作者喝杯咖啡表示鼓励</span
            >
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: "Index",
  data() {
    return {
      // 版本号
      version: "3.9.0"
    }
  },
  methods: {
    goTarget(href) {
      window.open(href, "_blank")
    }
  }
}
</script>

<style scoped lang="scss">
.home {
  blockquote {
    padding: 10px 20px;
    margin: 0 0 20px;
    font-size: 17.5px;
    border-left: 5px solid #eee;
  }
  hr {
    margin-top: 20px;
    margin-bottom: 20px;
    border: 0;
    border-top: 1px solid #eee;
  }
  .col-item {
    margin-bottom: 20px;
  }

  ul {
    padding: 0;
    margin: 0;
  }

  font-family: "open sans", "Helvetica Neue", Helvetica, Arial, sans-serif;
  font-size: 13px;
  color: #676a6c;
  overflow-x: hidden;

  ul {
    list-style-type: none;
  }

  h4 {
    margin-top: 0px;
  }

  h2 {
    margin-top: 10px;
    font-size: 26px;
    font-weight: 100;
  }

  p {
    margin-top: 10px;

    b {
      font-weight: 700;
    }
  }

  .update-log {
    ol {
      display: block;
      list-style-type: decimal;
      margin-block-start: 1em;
      margin-block-end: 1em;
      margin-inline-start: 0;
      margin-inline-end: 0;
      padding-inline-start: 40px;
    }
  }
}
</style>

