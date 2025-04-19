<template>
  <div class="login-container">
    <div class="login-card">
      <h2>{{ isLogin ? '欢迎登录' : '注册新账户' }}</h2>
      <form @submit.prevent="isLogin ? handleLogin() : handleRegister()">
        <input type="text" v-model="username" placeholder="用户名" required />
        <input type="password" v-model="password" placeholder="密码" required />

        <transition name="fade">
          <input v-if="!isLogin" type="password" v-model="confirmPassword" placeholder="确认密码" required />
        </transition>

        <button class="login-btn" type="submit">
          {{ isLogin ? '登录' : '注册' }}
        </button>

        <div class="links">
          <a @click.prevent="toggleMode">
            {{ isLogin ? '没有账号？注册' : '已有账号？登录' }}
          </a>
          <a @click.prevent="handleReset">重置密码</a>
        </div>
      </form>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { userApi } from "@/api";
import {useRouter} from 'vue-router'

const router = useRouter()
const username = ref('')
const password = ref('')
const confirmPassword = ref('')
const isLogin = ref(true)

const toggleMode = () => {
  isLogin.value = !isLogin.value
  password.value = ''
  confirmPassword.value = ''
}

const handleLogin = async () => {
  try {
    await userApi.login(
      username.value,
      password.value,
    )
    // 保存token到localStorage
    localStorage.setItem('token', 'res.token')
    alert('登录成功')
    debugger
    router.push('/')
  } catch (err: any) {
    alert(`登录失败: ${err.response?.data?.message || err.message}`)
  }
}

const handleRegister = async () => {
  if (password.value !== confirmPassword.value) {
    alert('两次密码不一致')
    return
  }
  try {
    await userApi.register(
      username.value,
      password.value,
      confirmPassword.value
    )
    alert('注册成功！请登录')
    // 注册成功后跳转页面...
    isLogin.value = true
  } catch (err: any) {
    alert(`注册失败: ${err.response?.data?.message || err.message}`)
  }
}

const handleReset = () => {
  alert('请联系管理员重置密码')
}
</script>

<style scoped>
h2 {
  color: #208;
}
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  width: 100vw;
  position: fixed;
  top: 0;
  left: 0;
  margin: 0;
  padding: 0;
  background: linear-gradient(135deg, #667eea, #764ba2);
}

.login-card {
  background-color: white;
  padding: 2rem 3rem;
  border-radius: 1rem;
  box-shadow: 0 20px 30px rgba(0, 0, 0, 0.2);
  text-align: center;
  width: 90%;
  max-width: 400px;
}

input {
  display: block;
  width: 100%;
  padding: 0.75rem;
  margin: 1rem 0;
  border: 1px solid #ccc;
  border-radius: 0.5rem;
}

.login-btn {
  background-color: #667eea;
  color: white;
  padding: 0.75rem;
  width: 100%;
  border: none;
  border-radius: 0.5rem;
  cursor: pointer;
  transition: background 0.3s ease;
}

.login-btn:hover {
  background-color: #5a67d8;
}

.links {
  margin-top: 1rem;
  display: flex;
  justify-content: space-between;
}

.links a {
  color: #667eea;
  text-decoration: underline;
  cursor: pointer;
  font-size: 0.9rem;
}

.fade-enter-active,
.fade-leave-active {
  transition: all 0.3s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
  transform: translateY(-10px);
}
</style>
