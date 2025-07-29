import request from '@/utils/request'

// 查询供应商信息列表
export function listVendors(query) {
  return request({
    url: '/system/vendors/list',
    method: 'get',
    params: query
  })
}

// 查询供应商信息详细
export function getVendors(id) {
  return request({
    url: '/system/vendors/' + id,
    method: 'get'
  })
}

// 新增供应商信息
export function addVendors(data) {
  return request({
    url: '/system/vendors',
    method: 'post',
    data: data
  })
}

// 修改供应商信息
export function updateVendors(data) {
  return request({
    url: '/system/vendors',
    method: 'put',
    data: data
  })
}

// 删除供应商信息
export function delVendors(id) {
  return request({
    url: '/system/vendors/' + id,
    method: 'delete'
  })
}
