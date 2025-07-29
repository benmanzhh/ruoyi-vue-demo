import request from '@/utils/request'

// 查询维护作业黑名单列表
export function listBlacklist(query) {
  return request({
    url: '/system/blacklist/list',
    method: 'get',
    params: query
  })
}

// 查询维护作业黑名单详细
export function getBlacklist(id) {
  return request({
    url: '/system/blacklist/' + id,
    method: 'get'
  })
}

// 新增维护作业黑名单
export function addBlacklist(data) {
  return request({
    url: '/system/blacklist',
    method: 'post',
    data: data
  })
}

// 修改维护作业黑名单
export function updateBlacklist(data) {
  return request({
    url: '/system/blacklist',
    method: 'put',
    data: data
  })
}

// 删除维护作业黑名单
export function delBlacklist(id) {
  return request({
    url: '/system/blacklist/' + id,
    method: 'delete'
  })
}
