import request from '@/utils/request'

// 查询供应商分类定义列表
export function listClassification(query) {
  return request({
    url: '/supplierpms/classification/list',
    method: 'get',
    params: query
  })
}

// 查询供应商分类定义详细
export function getClassification(classId) {
  return request({
    url: '/supplierpms/classification/' + classId,
    method: 'get'
  })
}

// 新增供应商分类定义
export function addClassification(data) {
  return request({
    url: '/supplierpms/classification',
    method: 'post',
    data: data
  })
}

// 修改供应商分类定义
export function updateClassification(data) {
  return request({
    url: '/supplierpms/classification',
    method: 'put',
    data: data
  })
}

// 删除供应商分类定义
export function delClassification(classId) {
  return request({
    url: '/supplierpms/classification/' + classId,
    method: 'delete'
  })
}
