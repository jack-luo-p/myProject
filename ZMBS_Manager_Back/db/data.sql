-- 用户相关初始化数据

-- 初始化用户数据
-- 密码默认为123456，经过MD5加密后为：e10adc3949ba59abbe56e057f20f883e
INSERT INTO `user` (`id`, `username`, `password`, `nickname`, `phone`, `email`, `avatar`, `status`, `create_time`, `update_time`, `is_deleted`)
VALUES (1, 'admin', 'e10adc3949ba59abbe56e057f20f883e', '系统管理员', '13800138000', 'admin@zmbs.com', NULL, 1, NOW(), NOW(), 0);

-- 初始化角色数据
INSERT INTO `role` (`id`, `name`, `code`, `description`, `status`, `create_time`, `update_time`, `is_deleted`)
VALUES 
(1, '超级管理员', 'ROLE_ADMIN', '系统超级管理员，拥有所有权限', 1, NOW(), NOW(), 0),
(2, '普通用户', 'ROLE_USER', '普通用户，拥有基本权限', 1, NOW(), NOW(), 0);

-- 用户角色关联
INSERT INTO `user_role` (`user_id`, `role_id`, `create_time`, `update_time`)
VALUES (1, 1, NOW(), NOW());

-- 初始化权限数据
INSERT INTO `permission` (`id`, `name`, `code`, `type`, `parent_id`, `path`, `icon`, `sort`, `status`, `create_time`, `update_time`, `is_deleted`)
VALUES 
-- 系统管理
(1, '系统管理', 'system:manage', 1, NULL, '/system', 'setting', 1, 1, NOW(), NOW(), 0),
-- 用户管理
(2, '用户管理', 'user:manage', 1, 1, '/system/user', 'user', 1, 1, NOW(), NOW(), 0),
(3, '用户查询', 'user:query', 3, 2, NULL, NULL, 1, 1, NOW(), NOW(), 0),
(4, '用户新增', 'user:add', 3, 2, NULL, NULL, 2, 1, NOW(), NOW(), 0),
(5, '用户修改', 'user:update', 3, 2, NULL, NULL, 3, 1, NOW(), NOW(), 0),
(6, '用户删除', 'user:delete', 3, 2, NULL, NULL, 4, 1, NOW(), NOW(), 0),
-- 角色管理
(7, '角色管理', 'role:manage', 1, 1, '/system/role', 'peoples', 2, 1, NOW(), NOW(), 0),
(8, '角色查询', 'role:query', 3, 7, NULL, NULL, 1, 1, NOW(), NOW(), 0),
(9, '角色新增', 'role:add', 3, 7, NULL, NULL, 2, 1, NOW(), NOW(), 0),
(10, '角色修改', 'role:update', 3, 7, NULL, NULL, 3, 1, NOW(), NOW(), 0),
(11, '角色删除', 'role:delete', 3, 7, NULL, NULL, 4, 1, NOW(), NOW(), 0),
-- 权限管理
(12, '权限管理', 'permission:manage', 1, 1, '/system/permission', 'lock', 3, 1, NOW(), NOW(), 0),
(13, '权限查询', 'permission:query', 3, 12, NULL, NULL, 1, 1, NOW(), NOW(), 0),
(14, '权限新增', 'permission:add', 3, 12, NULL, NULL, 2, 1, NOW(), NOW(), 0),
(15, '权限修改', 'permission:update', 3, 12, NULL, NULL, 3, 1, NOW(), NOW(), 0),
(16, '权限删除', 'permission:delete', 3, 12, NULL, NULL, 4, 1, NOW(), NOW(), 0);

-- 角色权限关联（超级管理员拥有所有权限）
INSERT INTO `role_permission` (`role_id`, `permission_id`, `create_time`, `update_time`)
VALUES 
(1, 1, NOW(), NOW()),
(1, 2, NOW(), NOW()),
(1, 3, NOW(), NOW()),
(1, 4, NOW(), NOW()),
(1, 5, NOW(), NOW()),
(1, 6, NOW(), NOW()),
(1, 7, NOW(), NOW()),
(1, 8, NOW(), NOW()),
(1, 9, NOW(), NOW()),
(1, 10, NOW(), NOW()),
(1, 11, NOW(), NOW()),
(1, 12, NOW(), NOW()),
(1, 13, NOW(), NOW()),
(1, 14, NOW(), NOW()),
(1, 15, NOW(), NOW()),
(1, 16, NOW(), NOW());

-- 普通用户权限（只有查询权限）
INSERT INTO `role_permission` (`role_id`, `permission_id`, `create_time`, `update_time`)
VALUES 
(2, 1, NOW(), NOW()),
(2, 2, NOW(), NOW()),
(2, 3, NOW(), NOW()),
(2, 7, NOW(), NOW()),
(2, 8, NOW(), NOW()),
(2, 12, NOW(), NOW()),
(2, 13, NOW(), NOW());

-- 菜单相关初始化数据

-- 初始化菜单数据
INSERT INTO `menu` (`id`, `parent_id`, `name`, `path`, `component`, `perms`, `icon`, `type`, `order_num`, `hidden`, `redirect`, `always_show`, `keep_alive`, `target`, `status`, `create_time`, `update_time`, `is_deleted`)
VALUES
-- 主目录
(1, NULL, '系统管理', '/system', 'Layout', NULL, 'system', 0, 1, 0, '/system/user', 1, 0, '_self', 1, NOW(), NOW(), 0),
(2, NULL, '监控中心', '/monitor', 'Layout', NULL, 'monitor', 0, 2, 0, '/monitor/server', 1, 0, '_self', 1, NOW(), NOW(), 0),
(3, NULL, '个人中心', '/profile', 'Layout', NULL, 'user', 0, 3, 0, '/profile/index', 0, 0, '_self', 1, NOW(), NOW(), 0),

-- 系统管理子菜单
(100, 1, '用户管理', 'user', 'system/user/index', 'system:user:list', 'user', 1, 1, 0, NULL, 0, 0, '_self', 1, NOW(), NOW(), 0),
(101, 1, '角色管理', 'role', 'system/role/index', 'system:role:list', 'peoples', 1, 2, 0, NULL, 0, 0, '_self', 1, NOW(), NOW(), 0),
(102, 1, '菜单管理', 'menu', 'system/menu/index', 'system:menu:list', 'tree-table', 1, 3, 0, NULL, 0, 0, '_self', 1, NOW(), NOW(), 0),
(103, 1, '权限管理', 'permission', 'system/permission/index', 'system:permission:list', 'lock', 1, 4, 0, NULL, 0, 0, '_self', 1, NOW(), NOW(), 0),

-- 监控中心子菜单
(200, 2, '服务监控', 'server', 'monitor/server/index', 'monitor:server:list', 'server', 1, 1, 0, NULL, 0, 0, '_self', 1, NOW(), NOW(), 0),
(201, 2, '登录日志', 'loginlog', 'monitor/loginlog/index', 'monitor:loginlog:list', 'logininfor', 1, 2, 0, NULL, 0, 0, '_self', 1, NOW(), NOW(), 0),

-- 个人中心
(300, 3, '个人信息', 'index', 'profile/index', 'profile:index', 'user', 1, 1, 0, NULL, 0, 0, '_self', 1, NOW(), NOW(), 0),
(301, 3, '修改密码', 'password', 'profile/password', 'profile:password', 'password', 1, 2, 0, NULL, 0, 0, '_self', 1, NOW(), NOW(), 0),

-- 用户管理按钮
(1001, 100, '用户查询', NULL, NULL, 'system:user:query', NULL, 2, 1, 0, NULL, 0, 0, '_self', 1, NOW(), NOW(), 0),
(1002, 100, '用户新增', NULL, NULL, 'system:user:add', NULL, 2, 2, 0, NULL, 0, 0, '_self', 1, NOW(), NOW(), 0),
(1003, 100, '用户修改', NULL, NULL, 'system:user:edit', NULL, 2, 3, 0, NULL, 0, 0, '_self', 1, NOW(), NOW(), 0),
(1004, 100, '用户删除', NULL, NULL, 'system:user:remove', NULL, 2, 4, 0, NULL, 0, 0, '_self', 1, NOW(), NOW(), 0),
(1005, 100, '重置密码', NULL, NULL, 'system:user:resetPwd', NULL, 2, 5, 0, NULL, 0, 0, '_self', 1, NOW(), NOW(), 0),

-- 角色管理按钮
(1006, 101, '角色查询', NULL, NULL, 'system:role:query', NULL, 2, 1, 0, NULL, 0, 0, '_self', 1, NOW(), NOW(), 0),
(1007, 101, '角色新增', NULL, NULL, 'system:role:add', NULL, 2, 2, 0, NULL, 0, 0, '_self', 1, NOW(), NOW(), 0),
(1008, 101, '角色修改', NULL, NULL, 'system:role:edit', NULL, 2, 3, 0, NULL, 0, 0, '_self', 1, NOW(), NOW(), 0),
(1009, 101, '角色删除', NULL, NULL, 'system:role:remove', NULL, 2, 4, 0, NULL, 0, 0, '_self', 1, NOW(), NOW(), 0),

-- 菜单管理按钮
(1010, 102, '菜单查询', NULL, NULL, 'system:menu:query', NULL, 2, 1, 0, NULL, 0, 0, '_self', 1, NOW(), NOW(), 0),
(1011, 102, '菜单新增', NULL, NULL, 'system:menu:add', NULL, 2, 2, 0, NULL, 0, 0, '_self', 1, NOW(), NOW(), 0),
(1012, 102, '菜单修改', NULL, NULL, 'system:menu:edit', NULL, 2, 3, 0, NULL, 0, 0, '_self', 1, NOW(), NOW(), 0),
(1013, 102, '菜单删除', NULL, NULL, 'system:menu:remove', NULL, 2, 4, 0, NULL, 0, 0, '_self', 1, NOW(), NOW(), 0);

-- 角色菜单关联（超级管理员拥有所有菜单权限）
INSERT INTO `role_menu` (`role_id`, `menu_id`, `create_time`, `update_time`)
VALUES 
-- 主目录
(1, 1, NOW(), NOW()),
(1, 2, NOW(), NOW()),
(1, 3, NOW(), NOW()),
-- 系统管理子菜单
(1, 100, NOW(), NOW()),
(1, 101, NOW(), NOW()),
(1, 102, NOW(), NOW()),
(1, 103, NOW(), NOW()),
-- 监控中心子菜单
(1, 200, NOW(), NOW()),
(1, 201, NOW(), NOW()),
-- 个人中心
(1, 300, NOW(), NOW()),
(1, 301, NOW(), NOW()),
-- 用户管理按钮
(1, 1001, NOW(), NOW()),
(1, 1002, NOW(), NOW()),
(1, 1003, NOW(), NOW()),
(1, 1004, NOW(), NOW()),
(1, 1005, NOW(), NOW()),
-- 角色管理按钮
(1, 1006, NOW(), NOW()),
(1, 1007, NOW(), NOW()),
(1, 1008, NOW(), NOW()),
(1, 1009, NOW(), NOW()),
-- 菜单管理按钮
(1, 1010, NOW(), NOW()),
(1, 1011, NOW(), NOW()),
(1, 1012, NOW(), NOW()),
(1, 1013, NOW(), NOW());

-- 普通用户拥有的菜单权限
INSERT INTO `role_menu` (`role_id`, `menu_id`, `create_time`, `update_time`)
VALUES 
-- 个人中心
(2, 3, NOW(), NOW()),
(2, 300, NOW(), NOW()),
(2, 301, NOW(), NOW()); 