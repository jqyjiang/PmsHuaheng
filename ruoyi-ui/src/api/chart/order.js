import request from '@/utils/request'

// 查询订单来源系统
export function listOrderSource() {
    return request({
      url: '/mast/material/listOrderSource',
      method: 'get',
    })
  }

  // 查询与月度订单
export function listMonthOrder() {
  return request({
    url: '/mast/material/listMonthOrder',
    method: 'get',
  })
}