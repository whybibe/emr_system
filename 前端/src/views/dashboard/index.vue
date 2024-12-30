<template>
  <div class="dashboard-container">
    <el-row>
      <el-col :span="12">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>病案住院详情数据</span>
          </div>
          <div id="medicalRecordNum" style="min-width:400px;height:400px"></div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>病案质量分析</span>
          </div>
          <div id="medicalRecordQuality" style="min-width:400px;height:400px"></div>
        </el-card>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="24">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>医院病例患病数统计</span>
          </div>
          <div id="caseLibrary" style="min-width:400px;min-height:800px"></div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>

import Highcharts from "highcharts";
import HighchartsMore from "highcharts/highcharts-more";
import HighchartsDrilldown from "highcharts/modules/drilldown";
import Highcharts3D from "highcharts/highcharts-3d";
import Highmaps from "highcharts/modules/map";

HighchartsMore(Highcharts);
HighchartsDrilldown(Highcharts);
Highcharts3D(Highcharts);
Highmaps(Highcharts);

import medicalStatisticsApi from '@/api/medical/medicalStatistics'
import caseLibraryApi from '@/api/medical/caseLibrary'

export default {

  data() {
    return {

    };
  },
  mounted() {
    this.fondMedicalRecordNum()
    this.findMedicalRecordQuality()
    this.findCaseLibrary()
  },
  methods: {
    fondMedicalRecordNum() {
      medicalStatisticsApi.medicalRecordNum()
      .then(res => {
        Highcharts.chart('medicalRecordNum', {
          chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false,
            type: 'pie'
          },
          title: {
            text: '病案住院详情'
          },
          tooltip: {
            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
          },
          plotOptions: {
            pie: {
              allowPointSelect: true,
              cursor: 'pointer',
              dataLabels: {
                enabled: true,
                format: '<b>{point.name}</b>: {point.percentage:.1f} %',
                style: {
                  color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                }
              }
            }
          },
          series: [{
            name: 'Brands',
            colorByPoint: true,
            data: res.data
          }]
        });
      })
    },
    findMedicalRecordQuality(){
      medicalStatisticsApi.medicalRecordQuality()
      .then(res => {
        Highcharts.chart('medicalRecordQuality',{
          chart: {
            type: 'column'
          },
          title: {
            text: '病案质量分析统计'
          },
          subtitle: {
            text: '病案质量评价汇总'
          },
          xAxis: {
            categories: [
              '极差','失望','一般','满意','惊喜'
            ],
            crosshair: true
          },
          yAxis: {
            min: 0,
            title: {
              text: '病案数量'
            }
          },
          tooltip: {
            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
              '<td style="padding:0"><b>{point.y:.1f} </b></td></tr>',
            footerFormat: '</table>',
            shared: true,
            useHTML: true
          },
          plotOptions: {
            column: {
              borderWidth: 0
            }
          },
          series: [{
            name: '病案质量数',
            data: res.data
          }]
        });
      })
    },
    findCaseLibrary() {
      caseLibraryApi.statistics().then(res => {
        Highcharts.chart('caseLibrary', {
          chart: {
            type: 'bar'
          },
          title: {
            text: '医院病例患病数统计'
          },
          subtitle: {
            text: '病例统计'
          },
          xAxis: {
            categories: res.data.name,
            title: {
              text: null
            }
          },
          yAxis: {
            min: 0,
            title: {
              text: '患病例人数',
              align: 'high'
            },
            labels: {
              overflow: 'justify'
            }
          },
          tooltip: {
            valueSuffix: ' 人'
          },
          plotOptions: {
            bar: {
              dataLabels: {
                enabled: true,
                allowOverlap: true // 允许数据标签重叠
              }
            }
          },
          legend: {
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'top',
            x: -40,
            y: 100,
            floating: true,
            borderWidth: 1,
            backgroundColor: ((Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF'),
            shadow: true
          },
          series: [{
            name: '病例人数',
            data: res.data.count
          }]
        });
      })

    }
  }
}

</script>

<style lang="scss" scoped>
.dashboard {
  &-container {
    margin: 10px;
  }
  &-text {
    font-size: 30px;
    line-height: 46px;
  }
}
</style>
