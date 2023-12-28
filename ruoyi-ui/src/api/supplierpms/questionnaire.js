import request from '@/utils/request'

// 查询调查表列表
export function listQuestionnaire(query) {
  return request({
    url: '/supplierpms/questionnaire/list',
    method: 'get',
    params: query
  })
}

// 查询调查表详细
export function getQuestionnaire(questionnaireId) {
  return request({
    url: '/supplierpms/questionnaire/' + questionnaireId,
    method: 'get'
  })
}

// 新增调查表
export function addQuestionnaire(data) {
  return request({
    url: '/supplierpms/questionnaire',
    method: 'post',
    data: data
  })
}

// 修改调查表
export function updateQuestionnaire(data) {
  return request({
    url: '/supplierpms/questionnaire',
    method: 'put',
    data: data
  })
}

// 删除调查表
export function delQuestionnaire(questionnaireId) {
  return request({
    url: '/supplierpms/questionnaire/' + questionnaireId,
    method: 'delete'
  })
}
