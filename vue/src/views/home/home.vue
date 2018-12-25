<template>

  <div id="a1">
    <Tabs :animated="false">
      <TabPane label="菲律宾">
        <div class="a2">
          <div v-for="resource in resources">
            <div v-if="resource.nation=='菲律宾'">
              <Menu active-name="1-2" :open-names="['1']" @on-select="route">
                <Submenu name="1">
                  <MenuItem v-if="resource.nation=='菲律宾'" :name="resource.url" :key="resource.url">{{resource.name}}
                  </MenuItem>
                </Submenu>
              </Menu>
            </div>
          </div>
        </div>

      </TabPane>
      <TabPane label="印尼">
        <div class="a2">
          <div v-for="resource in resources">
            <div v-if="resource.nation=='印尼'">
              <Menu active-name="1-2" :open-names="['1']" @on-select="route">
                <Submenu name="1">
                  <MenuItem v-if="resource.nation=='印尼'" :name="resource.url" :key="resource.url">{{resource.name}}
                  </MenuItem>
                </Submenu>
              </Menu>
            </div>
          </div>
        </div>
      </TabPane>
      <TabPane label="越南">
        <div class="a2">
          <div v-for="resource in resources">
            <div v-if="resource.nation=='越南'">
              <Menu active-name="1-2" :open-names="['1']" @on-select="route">
                <Submenu name="1">
                  <MenuItem v-if="resource.nation=='越南'" :name="resource.url" :key="resource.url">{{resource.name}}
                  </MenuItem>
                </Submenu>
              </Menu>
            </div>
          </div>
        </div>
      </TabPane>

      <TabPane label="用户管理">
        <div class="a2">
          <div v-if="type=='管理员'">
            <Button name="add" @click="modal1 = true">增加用户</Button>
            <Button name="update" @click.native="modal4 = true">修改用户</Button>
            <Button name="updatePwd" @click.native="modal2 = true">修改密码</Button>
            <Button name="updatePwd" @click.native="modal3 = true">重置密码</Button>
          </div>
          <div v-else>
            <Button name="updatePwd" @click.native="modal2 = true">修改密码</Button>
          </div>
        </div>
      </TabPane>

    </Tabs>
    <!--弹出层-->
    <div>
      <Modal
        v-model="modal1"
        title=" 增加用户"
        @on-ok="add"
        :loading="loading">
        <Form ref="formItem" :model="formItem" :rules="ruleValidate" :label-width="80">
          <FormItem label="邮箱" prop="email">
            <Input v-model="formItem.email" placeholder="xxx.@oriente.com"></Input>
          </FormItem>
          <FormItem label="角色" prop="role">
            <Select v-model="formItem.role">
              <Option value="管理员">管理员</Option>
              <Option value="用户">用户</Option>
            </Select>
          </FormItem>
          <FormItem label="权限" prop="competence">
            <CheckboxGroup v-model="formItem.competence" @on-change="select">
              <Checkbox v-for="item in allResources" :key="item.url" :label="item.id" :value="item.url" size="large">
                <span>{{item.name}}({{item.nation}})</span>
              </Checkbox>
            </CheckboxGroup>
          </FormItem>
          <!--<FormItem>-->
          <!--<Button type="primary">Submit</Button>-->
          <!--<Button style="margin-left: 8px">Cancel</Button>-->
          <!--</FormItem>-->
        </Form>
      </Modal>
    </div>
    <div>
      <Modal
        v-model="modal4"
        title="修改用户"
        @on-ok="update"
        :loading="loading">
        <Form ref="formItem" :model="formItem" :rules="ruleValidate" :label-width="80">
          <FormItem label="邮箱" prop="email">
            <Input v-model="formItem.email" placeholder="xxx.@oriente.com"></Input>
          </FormItem>
          <FormItem label="角色" prop="role">
            <Select v-model="formItem.role">
              <Option value="管理员">管理员</Option>
              <Option value="用户">用户</Option>
            </Select>
          </FormItem>
          <FormItem label="权限" prop="competence">
            <CheckboxGroup v-model="formItem.competence" @on-change="select">
              <Checkbox v-for="item in allResources" :key="item.url" :label="item.id" :value="item.url" size="large">
                <span>{{item.name}}({{item.nation}})</span>
              </Checkbox>
            </CheckboxGroup>
          </FormItem>
          <!--<FormItem>-->
          <!--<Button type="primary">Submit</Button>-->
          <!--<Button style="margin-left: 8px">Cancel</Button>-->
          <!--</FormItem>-->
        </Form>
      </Modal>
    </div>
    <div>
      <Modal
        v-model="modal2"
        title="修改密码"
        @on-ok="updatePwd"
        :loading="loading">
        <Form ref="formItem2" :model="formItem2" :rules="ruleValidate" :label-width="80">
          <FormItem label="旧密码" prop="oldPwd">
            <Input type="password" v-model="formItem2.oldPwd" placeholder="旧密码"></Input>
          </FormItem>
          <FormItem label="新密码" prop="newPwd">
            <Input type="password" v-model="formItem2.newPwd" placeholder="新密码"></Input>
          </FormItem>

          <!--<FormItem>-->
          <!--<Button type="primary">Submit</Button>-->
          <!--<Button style="margin-left: 8px">Cancel</Button>-->
          <!--</FormItem>-->
        </Form>
      </Modal>
    </div>
    <div>
      <Modal
        v-model="modal3"
        title="重置密码"
        @on-ok="resetPwd"
        :loading="loading">
        <Form ref="formItem" :model="formItem" :rules="ruleValidate" :label-width="80">
          <FormItem label="用户邮箱" prop="email">
            <Input v-model="formItem.email" placeholder="用户邮箱"></Input>
          </FormItem>


          <!--<FormItem>-->
          <!--<Button type="primary">Submit</Button>-->
          <!--<Button style="margin-left: 8px">Cancel</Button>-->
          <!--</FormItem>-->
        </Form>
      </Modal>
    </div>
  </div>

</template>
<script>
  import Button from "iview/src/components/button/button";

  var resour = "";

  export default {
    components: {Button},
    data() {
      var u = localStorage.getItem("user");
      var user = JSON.parse(u);
      var resources = user['user'].resourceList;
      console.log(resources);
      var type = user['user'].type;
      var allRes = localStorage.getItem("allResources");
      var allResources = JSON.parse(allRes)
      return {
        // 一个数组，参数是对象
        formItem: {
          input: '',
          select: '',
          checkbox: [],
        },
        formItem2: {
          olePwd: '',
          newPwd: '',
        },
        formItem3: {
          email: '',
          pwd: '',
        },
        ruleValidate: {
          email: [
            {required: true, message: 'Mailbox cannot be empty', trigger: 'blur'},
            {type: 'email', message: 'Incorrect email format', trigger: 'blur'}
          ],
          role: [
            {required: true, message: 'Please select the city', trigger: 'change'}
          ],
          competence: [
            {required: true, type: 'array', min: 1, message: 'Choose at least one hobby', trigger: 'change'},
            {type: 'array', max: 12, message: '', trigger: 'change'}
          ],
        },
        resources,
        type,
        allResources,
        modal1: false,
        modal2: false,
        modal3: false,
        modal4: false,
        loading: true,
      }
    },
    created: function () {
      this.axios.post('http://localhost:8080/user/getAllResources', {})
        .then(response => {
          if (response.data.code == 1) {
            var res = response.data.data;
            resour = res["resources"];
            console.log(resour);
            localStorage.setItem("allResources", JSON.stringify(resour));
          } else {

          }
        })
        .catch(function (error) {
          console.log(error);
        });
    },


    methods: {
      route(name) {
        alert("url:" + name);
      },
      add() {
        var email = this.formItem.email;
        var role = this.formItem.role;
        var competence = this.formItem.competence;
        if (email == null || role == null || competence == null) {
          this.$Message.info('请检查数据');
        } else {
          this.axios.post('http://localhost:8080/user/addUser', {
            email: email,
            type: role,
            competenceList: competence
          })
            .then(response => {
              if (response.data.code == 1) {
                this.$Message.info('添加成功');
              } else if (response.data.code = 100) {
                this.$router.push('/')
              }
              else {
                this.$Message.info(response.data.message);
              }
            })
            .catch(function (error) {
              console.log(error);
            });
          setTimeout(() => {
            this.loading = false;
            this.$nextTick(() => {
              this.loading = true;
              this.modal1 = false;
              this.$refs.formItem.resetFields();
            });
          }, 1000);
        }
      },
      updatePwd() {
        var oldPwd = this.formItem2.oldPwd;
        var newPwd = this.formItem2.newPwd;
        if (oldPwd == null || newPwd == null) {
          this.$Message.info('请检查数据');
        } else {
          this.axios.post('http://localhost:8080/user/updatePwd', {
            oldPwd: oldPwd,
            newPwd: newPwd
          })
            .then(response => {
              if (response.data.code == 1) {
                this.$Message.info('修改密码成功');
              } else if (response.data.code == 100) {
                this.$router.push('/')
              } else {
                this.$Message.info(response.data.message);
              }
            })
            .catch(function (error) {
              console.log(error);
            });
          setTimeout(() => {
            this.loading = false;
            this.$nextTick(() => {
              this.loading = true;
              this.modal2 = false;
              this.$refs.formItem2.resetFields();
            });
          }, 1000);
        }
      },
      resetPwd() {
        var email = this.formItem.email;
        if (email == null) {
          this.$Message.info('请检查数据');
        } else {
          this.axios.post('http://localhost:8080/user/reSetPwd', {
            email: email,
          })
            .then(response => {
              if (response.data.code == 1) {
                this.$Message.info('重置成功');
              } else if (response.data.code == 100) {
                this.$router.push('/')
              }
              else {
                this.$Message.info(response.data.message);
              }
            })
            .catch(function (error) {
              console.log(error);
            });
          setTimeout(() => {
            this.loading = false;
            this.$nextTick(() => {
              this.loading = true;
              this.modal3 = false;
              this.$refs.formItem.resetFields();
            });
          }, 1000);
        }

      },
      update() {
        var email = this.formItem.email;
        var role = this.formItem.role;
        var competence = this.formItem.competence;
        if (email == null || role == null || competence == null) {
          this.$Message.info('请检查数据');
        } else {
          this.axios.post('http://localhost:8080/user/updateCompetence', {
            email: email,
            type: role,
            competenceList: competence
          })
            .then(response => {
              if (response.data.code == 1) {
                this.$Message.info('修改成功');

              } else if (response.data.code == 1) {
                l
                this.$router.push('/')
              } else {

                this.$Message.info(response.data.message);
              }
            })
            .catch(function (error) {
              console.log(error);
            });
          setTimeout(() => {
            this.loading = false;
            this.$nextTick(() => {
              this.loading = true;
              this.modal4 = false;
              this.$refs.formItem.resetFields();
            });
          }, 1000);
        }
      },
    }
  }
</script>
<style>
  #a1 {
    width: 400px;
    height: 600px;
    margin-left: 450px;
  }

  .a2 {
    width: 200px;
    height: 600px;
    margin-left: 60px;
  }
</style>

