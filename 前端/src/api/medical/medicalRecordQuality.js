import request from '@/utils/request'

const api_name = '/admin/medical/medical_record_quality'

export default {
  list(param) {
    return request({
      url: `${api_name}/list`,
      method: 'GET',
      params: param
    })
  },
  add(data) {
    return request({
      url: `${api_name}/add`,
      method: 'POST',
      data: data
    })
  }
}
