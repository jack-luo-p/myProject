# 数据库初始化说明

本目录包含用于初始化ZMBS管理系统数据库的SQL脚本文件。

## 文件说明

- `schema.sql`: 所有表结构定义，包括用户表、角色表、权限表、菜单表及其关联表
- `data.sql`: 所有初始化数据，包含默认用户、角色、权限、菜单及其关联数据

## 初始化步骤

1. 首先创建数据库

```sql
CREATE DATABASE zmbs_manager DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
```

2. 执行表结构创建脚本

```bash
mysql -u用户名 -p密码 zmbs_manager < schema.sql
```

3. 执行初始化数据脚本

```bash
mysql -u用户名 -p密码 zmbs_manager < data.sql
```

## 默认账号信息

- 超级管理员
  - 用户名：admin
  - 密码：123456
  - 角色：超级管理员（拥有所有权限和菜单）

## 数据库设计说明

### 用户表 (user)
存储系统用户基本信息，包括账号、密码等。

### 角色表 (role)
定义系统角色，如管理员、普通用户等。

### 权限表 (permission)
定义系统权限项，包括菜单权限、按钮权限、接口权限等。

### 用户角色关联表 (user_role)
维护用户与角色之间的多对多关系。

### 角色权限关联表 (role_permission)
维护角色与权限之间的多对多关系。

### 用户登录日志表 (user_login_log)
记录用户登录信息，用于安全审计。

### 菜单表 (menu)
存储系统菜单信息，包括菜单名称、路径、图标等。支持多级菜单结构。

### 角色菜单关联表 (role_menu)
维护角色与菜单之间的多对多关系，用于控制不同角色可见的菜单项。 