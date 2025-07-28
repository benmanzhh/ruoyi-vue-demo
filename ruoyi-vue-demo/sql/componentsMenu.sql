-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('设备归档历史', '3', '1', 'components', 'system/components/index', 1, 0, 'C', '0', '0', 'system:components:list', '#', 'admin', sysdate(), '', null, '设备归档历史菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('设备归档历史查询', @parentId, '1',  '#', '', 1, 0, 'F', '0', '0', 'system:components:query',        '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('设备归档历史新增', @parentId, '2',  '#', '', 1, 0, 'F', '0', '0', 'system:components:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('设备归档历史修改', @parentId, '3',  '#', '', 1, 0, 'F', '0', '0', 'system:components:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('设备归档历史删除', @parentId, '4',  '#', '', 1, 0, 'F', '0', '0', 'system:components:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('设备归档历史导出', @parentId, '5',  '#', '', 1, 0, 'F', '0', '0', 'system:components:export',       '#', 'admin', sysdate(), '', null, '');