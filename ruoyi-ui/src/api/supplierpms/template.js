import request from '@/utils/request'

// 查询考评模板列表
export function listTemplate(query) {
  return request({
    url: '/supplierpms/template/list',
    method: 'get',
    params: query
  })
}

// 查询考评模板详细
export function getTemplate(scoringTemplateId) {
  return request({
    url: '/supplierpms/template/' + scoringTemplateId,
    method: 'get'
  })
}

// 新增考评模板
export function addTemplate(data) {
  return request({
    url: '/supplierpms/template',
    method: 'post',
    data: data
  })
}

// 修改考评模板
export function updateTemplate(data) {
  return request({
    url: '/supplierpms/template',
    method: 'put',
    data: data
  })
}

// 删除考评模板
export function delTemplate(scoringTemplateId) {
  return request({
    url: '/supplierpms/template/' + scoringTemplateId,
    method: 'delete'
  })
}
