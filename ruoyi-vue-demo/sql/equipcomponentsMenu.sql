-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('设备部件', '0', '1', 'equipcomponents', 'system/equipcomponents/index', 1, 0, 'C', '0', '0', 'system:equipcomponents:list', '#', 'admin', sysdate(), '', null, '设备部件菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('设备部件查询', @parentId, '1',  '#', '', 1, 0, 'F', '0', '0', 'system:equipcomponents:query',        '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('设备部件新增', @parentId, '2',  '#', '', 1, 0, 'F', '0', '0', 'system:equipcomponents:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('设备部件修改', @parentId, '3',  '#', '', 1, 0, 'F', '0', '0', 'system:equipcomponents:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('设备部件删除', @parentId, '4',  '#', '', 1, 0, 'F', '0', '0', 'system:equipcomponents:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('设备部件导出', @parentId, '5',  '#', '', 1, 0, 'F', '0', '0', 'system:equipcomponents:export',       '#', 'admin', sysdate(), '', null, '');