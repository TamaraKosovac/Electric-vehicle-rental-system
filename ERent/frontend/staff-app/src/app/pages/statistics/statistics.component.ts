import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatTabsModule } from '@angular/material/tabs';
import { MatIconModule } from '@angular/material/icon';
import { BaseChartDirective } from 'ng2-charts';
import { Chart, registerables, ChartData, ChartOptions } from 'chart.js';

// ➕ Dodano za formu i dropdown
import { FormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { MatOptionModule } from '@angular/material/core';

// ➕ Dodan servis
import { RentalsService } from '../../services/rentals.service';

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

  malfunctionsData = {
    labels: ['Cars', 'Bikes', 'Scooters'],
    datasets: [{ label: 'Malfunctions', data: [5, 3, 7] }]
  };

  revenueByVehicleTypeData = {
    labels: ['Cars', 'Bikes', 'Scooters'],
    datasets: [{ label: 'Revenue (€)', data: [1200, 800, 950] }]
  };

  constructor(private rentalsService: RentalsService) {}

  ngOnInit(): void {
    const today = new Date();
    this.selectedYear = today.getFullYear();
    this.selectedMonth = today.getMonth() + 1;
    this.loadDailyRevenue();
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
            label: 'Revenue (€)',
            data: sorted.map(d => d.totalRevenue),
            borderColor: '#2e6f6a',
            backgroundColor: 'rgba(46,111,106,0.2)',
            fill: true,
            tension: 0.3
          }
        ]
      };
    });
  }
}
