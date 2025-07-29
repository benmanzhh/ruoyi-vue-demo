import request from '@/utils/request'

// 查询维护人员信息列表
export function listPersonnel(query) {
  return request({
    url: '/system/personnel/list',
    method: 'get',
    params: query
  })
}

// 查询维护人员信息详细
export function getPersonnel(id) {
  return request({
    url: '/system/personnel/' + id,
    method: 'get'
  })
}

// 新增维护人员信息
export function addPersonnel(data) {
  return request({
    url: '/system/personnel',
    method: 'post',
    data: data
  })
}

// 修改维护人员信息
export function updatePersonnel(data) {
  return request({
    url: '/system/personnel',
    method: 'put',
    data: data
  })
}

// 删除维护人员信息
export function delPersonnel(id) {
  return request({
    url: '/system/personnel/' + id,
    method: 'delete'
  })
}
