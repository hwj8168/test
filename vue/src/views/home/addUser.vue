<template>
  <div>
    <Button type="primary" @click="modal1 = true">增加用户</Button>
    <!--<Modal-->
      <!--v-model="modal1"-->
      <!--title="增加用户"-->
      <!--@on-ok="ok"-->
      <!--:loading="loading">-->
      <Form ref="formValidate" :model="formValidate" :rules="ruleValidate" :label-width="80">
        <FormItem label="邮箱" prop="email">
          <Input v-model="formValidate.mail" placeholder="Enter your e-mail"></Input>
        </FormItem>
        <FormItem label="角色" prop="role">
          <Select v-model="formValidate.role">
            <Option value="管理员">管理员</Option>
            <Option value="用户">用户</Option>
          </Select>
        </FormItem>
        <FormItem label="权限" prop="competence">
          <CheckboxGroup v-model="formValidate.competence" @on-change="select">
            <Checkbox v-for="item in allResources" :key="item.url" :label="item.id" :value="item.url" size="large">
              <span>{{item.name}}({{item.nation}})</span>
            </Checkbox>
          </CheckboxGroup>
        </FormItem>

      </Form>

    <!--</Modal>-->
  </div>
</template>
<script>
  import Button from "iview/src/components/button/button";

  var resour = "";

  export default {
    components: {Button},
    data() {
      return {
        // 一个数组，参数是对象
        formValidate: {
          mail: '',
          role: '',
          competence: []
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
        loading: true,
      }
    }
  }
</script>
