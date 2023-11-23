import request from '@/utils/request'

// 查询采购需求申请列表
export function listRequirement(query) {
  return request({
    url: '/demand/requirement/list',
    method: 'get',
    params: query
  })
}

// 查询采购需求申请详细
export function getRequirement(requirementId) {
  return request({
    url: '/demand/requirement/' + requirementId,
    method: 'get'
  })
}

// 新增采购需求申请
export function addRequirement(data) {
  return request({
    url: '/demand/requirement',
    method: 'post',
    data: data
  })
}

// 修改采购需求申请
export function updateRequirement(data) {
  return request({
    url: '/demand/requirement',
    method: 'put',
    data: data
  })
}

// 删除采购需求申请
export function delRequirement(requirementId) {
  return request({
    url: '/demand/requirement/' + requirementId,
    method: 'delete'
  })
}
