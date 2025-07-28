import request from '@/utils/request'

// 查询设备归档历史列表
export function listComponents(query) {
  return request({
    url: '/system/components/list',
    method: 'get',
    params: query
  })
}

// 查询设备归档历史详细
export function getComponents(id) {
  return request({
    url: '/system/components/' + id,
    method: 'get'
  })
}

// 新增设备归档历史
export function addComponents(data) {
  return request({
    url: '/system/components',
    method: 'post',
    data: data
  })
}

// 修改设备归档历史
export function updateComponents(data) {
  return request({
    url: '/system/components',
    method: 'put',
    data: data
  })
}

// 删除设备归档历史
export function delComponents(id) {
  return request({
    url: '/system/components/' + id,
    method: 'delete'
  })
}
