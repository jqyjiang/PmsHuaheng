import request from '@/utils/request'

// 查询银行类型列表
export function listBank_type(query) {
  return request({
    url: '/mast/bank_type/list',
    method: 'get',
    params: query
  })
}

// 查询银行类型详细
export function getBank_type(bankTypeId) {
  return request({
    url: '/mast/bank_type/' + bankTypeId,
    method: 'get'
  })
}

// 新增银行类型
export function addBank_type(data) {
  return request({
    url: '/mast/bank_type',
    method: 'post',
    data: data
  })
}

// 修改银行类型
export function updateBank_type(data) {
  return request({
    url: '/mast/bank_type',
    method: 'put',
    data: data
  })
}

// 删除银行类型
export function delBank_type(bankTypeId) {
  return request({
    url: '/mast/bank_type/' + bankTypeId,
    method: 'delete'
  })
}
