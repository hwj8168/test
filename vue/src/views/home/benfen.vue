<template>
  <div>
    <Menu active-name="1-2" :open-names="['1']">
      <Submenu name="1">
        <template slot="title">
          <Icon type="ios-analytics"/>
          我的权限
        </template>
        <li v-for="resource in resources">
          <MenuItem>{{resource.name}}</MenuItem>
        </li>
      </Submenu>
      <div v-if="type=='管理员'">
        <Submenu name="2">
          <template slot="title">
            <Icon type="ios-filing"/>
            用户管理
          </template>
          <MenuItem name="2-1">增加用户</MenuItem>
          <MenuItem name="2-2">修改用户</MenuItem>
        </Submenu>
      </div>
    </Menu>
  </div>
</template>
<script>
  export default {
    data() {
      var u = localStorage.getItem("user");
      var user = JSON.parse(u);
      console.log(user);
      var resources = user['user'].resourceList;
      var type = user['user'].type;
      console.log(type);
      console.log(resources);//Type, Height
      return {
        // 一个数组，参数是对象
        resources,
        type
      }
    }
  }
</script>
