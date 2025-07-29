import request from '@/utils/request'

// 查询设备部件列表
export function listEquipcomponents(query) {
  return request({
    url: '/system/equipcomponents/list',
    method: 'get',
    params: query
  })
}

// 查询设备部件详细
export function getEquipcomponents(id) {
  return request({
    url: '/system/equipcomponents/' + id,
    method: 'get'
  })
}

// 新增设备部件
export function addEquipcomponents(data) {
  return request({
    url: '/system/equipcomponents',
    method: 'post',
    data: data
  })
}

// 修改设备部件
export function updateEquipcomponents(data) {
  return request({
    url: '/system/equipcomponents',
    method: 'put',
    data: data
  })
}

// 删除设备部件
export function delEquipcomponents(id) {
  return request({
    url: '/system/equipcomponents/' + id,
    method: 'delete'
  })
}
