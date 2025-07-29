-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('设备主档', '0', '1', 'master', 'system/master/index', 1, 0, 'C', '0', '0', 'system:master:list', '#', 'admin', sysdate(), '', null, '设备主档菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('设备主档查询', @parentId, '1',  '#', '', 1, 0, 'F', '0', '0', 'system:master:query',        '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('设备主档新增', @parentId, '2',  '#', '', 1, 0, 'F', '0', '0', 'system:master:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('设备主档修改', @parentId, '3',  '#', '', 1, 0, 'F', '0', '0', 'system:master:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('设备主档删除', @parentId, '4',  '#', '', 1, 0, 'F', '0', '0', 'system:master:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('设备主档导出', @parentId, '5',  '#', '', 1, 0, 'F', '0', '0', 'system:master:export',       '#', 'admin', sysdate(), '', null, '');