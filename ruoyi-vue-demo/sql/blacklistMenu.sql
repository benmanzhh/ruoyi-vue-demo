-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('维护作业黑名单', '3', '1', 'blacklist', 'system/blacklist/index', 1, 0, 'C', '0', '0', 'system:blacklist:list', '#', 'admin', sysdate(), '', null, '维护作业黑名单菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('维护作业黑名单查询', @parentId, '1',  '#', '', 1, 0, 'F', '0', '0', 'system:blacklist:query',        '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('维护作业黑名单新增', @parentId, '2',  '#', '', 1, 0, 'F', '0', '0', 'system:blacklist:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('维护作业黑名单修改', @parentId, '3',  '#', '', 1, 0, 'F', '0', '0', 'system:blacklist:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('维护作业黑名单删除', @parentId, '4',  '#', '', 1, 0, 'F', '0', '0', 'system:blacklist:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('维护作业黑名单导出', @parentId, '5',  '#', '', 1, 0, 'F', '0', '0', 'system:blacklist:export',       '#', 'admin', sysdate(), '', null, '');