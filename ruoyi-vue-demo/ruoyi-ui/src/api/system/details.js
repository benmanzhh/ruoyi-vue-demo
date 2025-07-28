import request from '@/utils/request'

// 查询维护作业完成情况详情列表
export function listDetails(query) {
  return request({
    url: '/system/details/list',
    method: 'get',
    params: query
  })
}

// 查询维护作业完成情况详情详细
export function getDetails(id) {
  return request({
    url: '/system/details/' + id,
    method: 'get'
  })
}

// 新增维护作业完成情况详情
export function addDetails(data) {
  return request({
    url: '/system/details',
    method: 'post',
    data: data
  })
}

// 修改维护作业完成情况详情
export function updateDetails(data) {
  return request({
    url: '/system/details',
    method: 'put',
    data: data
  })
}

// 删除维护作业完成情况详情
export function delDetails(id) {
  return request({
    url: '/system/details/' + id,
    method: 'delete'
  })
}
