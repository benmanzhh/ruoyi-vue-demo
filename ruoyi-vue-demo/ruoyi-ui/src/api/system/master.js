import request from '@/utils/request'

//// 获取质保提醒数据
//export function getWarrantyAlerts() {
//  return request({
//    url: '/warranty-alerts',
//    method: 'get'
//  })
//}
//
//
//// 获取生命周期提醒数据
//export function getLifecycleAlerts() {
//  return request({
//    url: '/lifecycle-alerts',
//    method: 'get'
//  })
//}
// 获取统一的生命周期与质保提醒数据
export function getCombinedAlerts() {
  return request({
    url: '/combined-alerts',
    method: 'get'
  })
}

// 查询设备主档列表
export function listMaster(query) {
  return request({
    url: '/system/master/list',
    method: 'get',
    params: query
  })
}

// 查询设备主档详细
export function getMaster(id) {
  return request({
    url: '/system/master/' + id,
    method: 'get'
  })
}

// 新增设备主档
export function addMaster(data) {
  return request({
    url: '/system/master',
    method: 'post',
    data: data
  })
}

// 修改设备主档
export function updateMaster(data) {
  return request({
    url: '/system/master',
    method: 'put',
    data: data
  })
}

// 删除设备主档
export function delMaster(id) {
  return request({
    url: '/system/master/' + id,
    method: 'delete'
  })
}
