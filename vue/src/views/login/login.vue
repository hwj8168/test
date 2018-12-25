<template>
  <div>
    <div class="login-wrap" v-show="showLogin">
      <h3>登录</h3>
      <p v-show="showTishi">{{tishi}}</p>
      <input type="text" placeholder="请输入用户名" v-model="username">
      <input type="password" placeholder="请输入密码" v-model="password">
      <button v-on:click="login">登录</button>
    </div>
  </div>
</template>

<style>
  .login-wrap {
    text-align: center;
    margin-top: 80px;
  }

  input {
    display: block;
    width: 250px;
    height: 40px;
    line-height: 40px;
    margin: 0 auto;
    margin-bottom: 10px;
    outline: none;
    border: 1px solid #888;
    padding: 10px;
    box-sizing: border-box;
  }

  p {
    color: red;
  }

  button {
    display: block;
    width: 250px;
    height: 40px;
    line-height: 40px;
    margin: 0 auto;
    border: none;
    background-color: #41b883;
    color: #fff;
    font-size: 16px;
    margin-bottom: 5px;
  }

  span {
    cursor: pointer;
  }

  span:hover {
    color: #41b883;
  }
</style>

<script>
  export default {
    data() {
      return {
        showLogin: true,
        showRegister: false,
        showTishi: false,
        tishi: '',
        username: '',
        password: '',
        newUsername: '',
        newPassword: ''
      }
    },

    methods: {
      login() {
        if (this.username == "" || this.password == "") {
          alert("请输入用户名或密码")
        } else {
          //let data = {'email': this.username, 'password': this.password}
          /*接口请求*/
          this.axios.post('http://localhost:8080/user/login', {
            email: this.username,
            password: this.password
          })
            .then(response => {
              if (response.data.code == 0) {
                this.tishi = response.data.message;
                this.showTishi = true
              } else {
                console.log(response.headers.token)
                console.log(response.data)
                localStorage.setItem("token",response.headers.token);
                var user=JSON.stringify(response.data.data);
                localStorage.setItem("user",user);
                this.$router.push('/home')
              }
            })
            .catch(function (error) {
              console.log(error);
            });
        }
      }
    }
  }
</script>
