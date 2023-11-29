import request from '@/utils/request'

// 查询行业类型列表
export function listIndustry_type(query) {
  return request({
    url: '/mast/industry_type/list',
    method: 'get',
    params: query
  })
}

// 查询行业类型详细
export function getIndustry_type(industryTypeId) {
  return request({
    url: '/mast/industry_type/' + industryTypeId,
    method: 'get'
  })
}

// 新增行业类型
export function addIndustry_type(data) {
  return request({
    url: '/mast/industry_type',
    method: 'post',
    data: data
  })
}

// 修改行业类型
export function updateIndustry_type(data) {
  return request({
    url: '/mast/industry_type',
    method: 'put',
    data: data
  })
}

// 删除行业类型
export function delIndustry_type(industryTypeId) {
  return request({
    url: '/mast/industry_type/' + industryTypeId,
    method: 'delete'
  })
}
