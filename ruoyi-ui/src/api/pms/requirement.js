import request from '@/utils/request'

// 查询需求物料申请表列表
export function listRequirement(query) {
  return request({
    url: '/pms/requirement/list',
    method: 'get',
    params: query
  })
}

// 查询需求物料申请表详细
export function getRequirement(requirementId) {
  return request({
    url: '/pms/requirement/' + requirementId,
    method: 'get'
  })
}

// 新增需求物料申请表
export function addRequirement(data) {
  return request({
    url: '/pms/requirement',
    method: 'post',
    data: data
  })
}

// 修改需求物料申请表
export function updateRequirement(data) {
  return request({
    url: '/pms/requirement',
    method: 'put',
    data: data
  })
}

// 删除需求物料申请表
export function delRequirement(requirementId) {
  return request({
    url: '/pms/requirement/' + requirementId,
    method: 'delete'
  })
}
