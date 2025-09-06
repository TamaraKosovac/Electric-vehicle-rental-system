import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatTabsModule } from '@angular/material/tabs';
import { MatIconModule } from '@angular/material/icon';
import { BaseChartDirective } from 'ng2-charts';
import { Chart, registerables, ChartData, ChartOptions, ChartDataset } from 'chart.js';
import { FormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { MatOptionModule } from '@angular/material/core';
import { RentalsService } from '../../services/rentals.service';
import { MalfunctionsService } from '../../services/malfunctions.service';

Chart.register(...registerables);

@Component({
  selector: 'app-statistics',
  standalone: true,
  imports: [
    CommonModule,
    MatTabsModule,
    MatIconModule,
    BaseChartDirective,
    FormsModule,
    MatFormFieldModule,
    MatSelectModule,
    MatOptionModule
  ],
  templateUrl: './statistics.component.html',
  styleUrls: ['./statistics.component.css']
})
export class StatisticsComponent implements OnInit {
  selectedMonth!: number;
  selectedYear!: number;

  months = [
    { value: 1, name: 'January' },
    { value: 2, name: 'February' },
    { value: 3, name: 'March' },
    { value: 4, name: 'April' },
    { value: 5, name: 'May' },
    { value: 6, name: 'June' },
    { value: 7, name: 'July' },
    { value: 8, name: 'August' },
    { value: 9, name: 'September' },
    { value: 10, name: 'October' },
    { value: 11, name: 'November' },
    { value: 12, name: 'December' }
  ];

  dailyRevenueData: ChartData<'line'> = { labels: [], datasets: [] };
  dailyRevenueOptions: ChartOptions<'line'> = { responsive: true };

  malfunctionsData: ChartData<'bar'> = { labels: [], datasets: [] };

  revenueByVehicleTypeData: ChartData<'pie'> = { labels: [], datasets: [] };

  constructor(
    private rentalsService: RentalsService,
    private malfunctionsService: MalfunctionsService
  ) {}

  ngOnInit(): void {
    const today = new Date();
    this.selectedYear = today.getFullYear();
    this.selectedMonth = today.getMonth() + 1;
    this.loadDailyRevenue();
    this.loadMalfunctions();
    this.loadRevenueByVehicleType();
  }

  loadDailyRevenue(): void {
    this.rentalsService.getDailyRevenue(this.selectedYear, this.selectedMonth).subscribe(data => {
      const sorted = data.sort(
        (a, b) => new Date(a.date).getTime() - new Date(b.date).getTime()
      );

      this.dailyRevenueData = {
        labels: sorted.map(d =>
          new Date(d.date).toLocaleDateString('en-GB', {
            day: '2-digit',
            month: '2-digit'
          })
        ),
        datasets: [
          {
            label: 'Revenue (KM)',
            data: sorted.map(d => d.totalRevenue),
            backgroundColor: 'rgba(46,111,106,0.2)',
            borderColor: '#2e6f6a', 
            borderWidth: 0.5
          }
        ]
      };
    });
  }

  loadMalfunctions(): void {
    this.malfunctionsService.getMalfunctionsByVehicleType().subscribe(data => {
      this.malfunctionsData = {
        labels: data.map(d => d.label),
        datasets: [
          {
            label: 'Malfunctions',
            data: data.map(d => d.value),
            backgroundColor: 'rgba(46,111,106,0.2)', 
            borderColor: '#2e6f6a', 
            borderWidth: 0.5
          } as ChartDataset<'bar'>
        ]
      };
    });
  }

  loadRevenueByVehicleType(): void {
    this.rentalsService.getRevenueByVehicleType().subscribe(data => {
      this.revenueByVehicleTypeData = {
        labels: data.map(d => d.label),
        datasets: [
          {
            label: 'Revenue (KM)',
            data: data.map(d => d.value),
            backgroundColor: 'rgba(46,111,106,0.2)', 
            borderColor: '#2e6f6a', 
            borderWidth: 0.5
          } as ChartDataset<'pie'>
        ]
      };
    });
  }
}
