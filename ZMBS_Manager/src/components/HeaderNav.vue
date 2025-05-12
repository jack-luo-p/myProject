<template>
  <div class="header-nav">
    <div class="left">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item v-for="(item, index) in breadcrumbs" :key="index" :to="item.path">
          {{ item.name }}
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    
    <div class="right">
      <el-dropdown trigger="click">
        <div class="user-info">
          <el-avatar :size="32" :src="userAvatar">{{ userName.charAt(0) }}</el-avatar>
          <span class="user-name">{{ userName }}</span>
        </div>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item @click="goToProfile">
              <el-icon><User /></el-icon>个人信息
            </el-dropdown-item>
            <el-dropdown-item @click="handleLogout">
              <el-icon><SwitchButton /></el-icon>退出登录
            </el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { User, SwitchButton } from '@element-plus/icons-vue'
import { ElMessageBox } from 'element-plus'
import userApi from '@/api/user'

const router = useRouter()
const route = useRoute()

// 假设用户信息
const userName = ref('管理员')
const userAvatar = ref('')

// 生成面包屑
const breadcrumbs = computed(() => {
  const path = route.path
  const pathArray = path.split('/').filter(Boolean)
  const result = [{ name: '首页', path: '/' }]
  
  let currentPath = ''
  pathArray.forEach(item => {
    currentPath += `/${item}`
    result.push({
      name: item.charAt(0).toUpperCase() + item.slice(1),
      path: currentPath
    })
  })
  
  return result
})

const goToProfile = () => {
  router.push('/settings/profile')
}

const handleLogout = () => {
  ElMessageBox.confirm('确定要退出登录吗?', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    try {
      await userApi.logout()
      localStorage.removeItem('token')
      router.push('/login')
    } catch (error) {
      console.error('退出登录失败:', error)
    }
  }).catch(() => {})
}
</script>

<style scoped>
.header-nav {
  height: 60px;
  background-color: white;
  box-shadow: 0 1px 4px rgba(0, 21, 41, 0.08);
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 20px;
}

.user-info {
  display: flex;
  align-items: center;
  cursor: pointer;
}

.user-name {
  margin-left: 8px;
  font-size: 14px;
}
</style> 