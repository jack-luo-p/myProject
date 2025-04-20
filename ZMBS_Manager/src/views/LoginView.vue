<template>
  <div class="login-container">
    <div class="login-form-container">
      <div class="login-logo">
        <h1>ZMBS管理系统</h1>
      </div>
      <el-card class="login-card" :body-style="{ padding: '30px' }">
        <h2 class="login-title">{{ isLogin ? '欢迎回来' : '创建新账户' }}</h2>
        <p class="login-subtitle">{{ isLogin ? '请输入您的凭证登录系统' : '请填写以下信息完成注册' }}</p>
        
        <el-form :model="formData" ref="formRef" :rules="rules" label-position="top" size="large">
          <el-form-item label="用户名" prop="username">
            <el-input 
              v-model="formData.username" 
              placeholder="请输入用户名" 
              :prefix-icon="User"
              autocomplete="username">
            </el-input>
          </el-form-item>
          
          <el-form-item label="密码" prop="password">
            <el-input 
              v-model="formData.password" 
              type="password" 
              placeholder="请输入密码" 
              :prefix-icon="Lock"
              show-password
              autocomplete="current-password">
            </el-input>
          </el-form-item>
          
          <el-form-item v-if="!isLogin" label="确认密码" prop="confirmPassword">
            <el-input 
              v-model="formData.confirmPassword" 
              type="password" 
              placeholder="请再次输入密码" 
              :prefix-icon="Lock"
              show-password
              autocomplete="new-password">
            </el-input>
          </el-form-item>

          <el-form-item>
            <el-button 
              type="primary" 
              :loading="loading" 
              class="submit-btn" 
              @click="isLogin ? submitLogin() : submitRegister()"
              round>
              {{ isLogin ? '登 录' : '注 册' }}
            </el-button>
          </el-form-item>
        </el-form>
        
        <div class="login-options">
          <el-checkbox v-if="isLogin" v-model="rememberMe">记住我</el-checkbox>
          <el-link v-if="isLogin" type="primary" @click="handleReset" class="forgot-password">忘记密码?</el-link>
        </div>
        
        <div class="login-divider">
          <el-divider>
            <span class="divider-text">{{ isLogin ? '还没有账号?' : '已有账号?' }}</span>
          </el-divider>
        </div>
        
        <el-button 
          type="default" 
          class="toggle-btn" 
          @click="toggleMode"
          plain>
          {{ isLogin ? '立即注册' : '返回登录' }}
        </el-button>
      </el-card>
      
      <div class="login-footer">
        <p>© {{ new Date().getFullYear() }} ZMBS 管理系统. 保留所有权利</p>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { User, Lock } from '@element-plus/icons-vue'
import userApi from '@/api/user'

// 使用类型声明
type FormInstance = InstanceType<typeof import('element-plus')['ElForm']>

const router = useRouter()
const formRef = ref<FormInstance>()
const loading = ref(false)
const isLogin = ref(true)
const rememberMe = ref(false)

// 表单数据
const formData = reactive({
  username: '',
  password: '',
  confirmPassword: ''
})

// 表单验证规则
const rules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 3, max: 20, message: '长度在3到20个字符', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, message: '密码长度至少6个字符', trigger: 'blur' }
  ],
  confirmPassword: [
    { 
      required: true, 
      message: '请再次输入密码', 
      trigger: 'blur' 
    },
    { 
      validator: (rule: any, value: string, callback: any) => {
        if (value !== formData.password) {
          callback(new Error('两次输入密码不一致'))
        } else {
          callback()
        }
      }, 
      trigger: 'blur' 
    }
  ]
}

const toggleMode = () => {
  isLogin.value = !isLogin.value
  formData.password = ''
  formData.confirmPassword = ''
}

const submitLogin = async () => {
  if (!formRef.value) return
  
  await formRef.value.validate(async (valid) => {
    if (valid) {
      loading.value = true
      try {
        const res = await userApi.login(
          formData.username,
          formData.password
        )
        // 保存token到localStorage
        localStorage.setItem('token', res.data.token)
        
        // 如果选择了记住我，可以保存用户名
        if (rememberMe.value) {
          localStorage.setItem('rememberedUsername', formData.username)
        }
        
        // 使用Element UI的消息通知替代alert
        ElMessage({
          type: 'success',
          message: '登录成功',
          duration: 2000
        })
        
        setTimeout(() => {
          router.push('/')
        }, 1000)
      } catch (err: any) {
        ElMessage({
          type: 'error',
          message: `登录失败: ${err.response?.data?.message || err.message}`
        })
      } finally {
        loading.value = false
      }
    }
  })
}

const submitRegister = async () => {
  if (!formRef.value) return
  
  await formRef.value.validate(async (valid) => {
    if (valid) {
      loading.value = true
      try {
        await userApi.register(
          formData.username,
          formData.password,
          formData.confirmPassword
        )
        
        ElMessage({
          type: 'success',
          message: '注册成功！请登录',
          duration: 2000
        })
        
        isLogin.value = true
        formData.password = ''
        formData.confirmPassword = ''
      } catch (err: any) {
        // 显示具体错误信息
        ElMessage({
          type: 'error',
          message: err.message || '注册失败，请稍后再试',
          duration: 3000
        })
      } finally {
        loading.value = false
      }
    }
  })
}

const handleReset = () => {
  ElMessage({
    type: 'info',
    message: '请联系管理员重置密码'
  })
}
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  width: 100%;
  position: fixed;
  top: 0;
  left: 0;
  margin: 0;
  padding: 20px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  box-sizing: border-box;
}

.login-form-container {
  width: 100%;
  max-width: 450px;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.login-logo {
  margin-bottom: 20px;
  text-align: center;
}

.login-logo h1 {
  color: white;
  font-size: 28px;
  margin: 0;
  text-shadow: 0px 2px 4px rgba(0, 0, 0, 0.2);
}

.login-card {
  width: 100%;
  border-radius: 12px;
  box-shadow: 0 15px 30px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
}

.login-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 20px 40px rgba(0, 0, 0, 0.2);
}

.login-title {
  font-size: 24px;
  text-align: center;
  margin-top: 0;
  margin-bottom: 10px;
  color: #333;
}

.login-subtitle {
  font-size: 14px;
  text-align: center;
  margin-bottom: 25px;
  color: #888;
}

.submit-btn {
  width: 100%;
  height: 44px;
  font-size: 16px;
  letter-spacing: 1px;
  font-weight: 500;
}

.login-options {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 5px;
  margin-bottom: 15px;
}

.forgot-password {
  font-size: 14px;
}

.login-divider {
  margin: 25px 0;
}

.divider-text {
  color: #909399;
  font-size: 14px;
}

.toggle-btn {
  width: 100%;
  height: 44px;
  font-size: 16px;
}

.login-footer {
  margin-top: 25px;
  text-align: center;
  color: rgba(255, 255, 255, 0.8);
  font-size: 14px;
}

@media (max-width: 480px) {
  .login-logo h1 {
    font-size: 24px;
  }
  
  .login-card {
    box-shadow: 0 10px 20px rgba(0, 0, 0, 0.1);
  }
  
  .login-title {
    font-size: 20px;
  }
  
  .login-footer {
    font-size: 12px;
  }
}
</style>
