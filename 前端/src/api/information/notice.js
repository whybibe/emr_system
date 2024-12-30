import request from '@/utils/request'

const api_name = '/admin/msg/hospNotice'

export default {
  findNoticeType() {
    return request({
      url: `${api_name}/anno/enums`,
      method: 'GET'
    })
  },
  saveNotice(data){
    return request({
      url: `${api_name}/save`,
      method: 'POST',
      data: data
    })
  },
  findHospNoticeList(data){
    return request({
      url: `${api_name}/list`,
      method: 'GET',
      params: data
    })
  },
  updateNotice(data){
    return request({
      url: `${api_name}/update`,
      method: 'put',
      data: data
    })
  },
  deleteNotice(id){
    return request({
      url: `${api_name}/delete/${id}`,
      method: 'delete',
    })
  }
}
