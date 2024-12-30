import request from '@/utils/request'

const api_name = '/admin/medical/statistics/anno'

export default {
  medicalRecordNum() {
    return request({
      url: `${api_name}/medicalRecordNum`,
      method: 'GET'
    })
  },
  medicalRecordQuality(){
    return request({
      url: `${api_name}/medicalRecordQuality`,
      method: 'GET'
    })
  }
}
