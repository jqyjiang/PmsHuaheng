import request from '@/utils/request'

// 查询币种列表
export function listCurrency(query) {
  return request({
    url: '/mast/currency/list',
    method: 'get',
    params: query
  })
}

// 查询币种详细
export function getCurrency(currencyId) {
  return request({
    url: '/mast/currency/' + currencyId,
    method: 'get'
  })
}

// 新增币种
export function addCurrency(data) {
  return request({
    url: '/mast/currency',
    method: 'post',
    data: data
  })
}

// 修改币种
export function updateCurrency(data) {
  return request({
    url: '/mast/currency',
    method: 'put',
    data: data
  })
}

// 删除币种
export function delCurrency(currencyId) {
  return request({
    url: '/mast/currency/' + currencyId,
    method: 'delete'
  })
}
