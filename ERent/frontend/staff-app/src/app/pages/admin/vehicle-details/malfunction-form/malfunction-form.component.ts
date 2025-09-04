import { Component, Inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule, provideNativeDateAdapter } from '@angular/material/core';
import { Malfunction } from '../../../../models/malfunction.model';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-malfunction-form',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatIconModule
  ],
  providers: [provideNativeDateAdapter()],
  templateUrl: './malfunction-form.component.html',
  styleUrls: ['./malfunction-form.component.css']
})
export class MalfunctionFormComponent {
  malfunction: any = {
    id: 0,
    description: '',
    dateTime: '',
    vehicleId: 0,
    date: undefined,
    time: ''
  };

  constructor(
    public dialogRef: MatDialogRef<MalfunctionFormComponent>,
    @Inject(MAT_DIALOG_DATA) public data?: { malfunction?: Malfunction }
  ) {
    if (data?.malfunction) {
      this.malfunction = { ...data.malfunction };

      if (this.malfunction.dateTime) {
        const dt = new Date(this.malfunction.dateTime);
        this.malfunction.date = dt;

        this.malfunction.time =
          dt.getHours().toString().padStart(2, '0') +
          ':' +
          dt.getMinutes().toString().padStart(2, '0');
      }
    }
  }

  save() {
    if (this.malfunction.date && this.malfunction.time) {
      const date = new Date(this.malfunction.date);
      const [hours, minutes] = this.malfunction.time.split(':');
      date.setHours(+hours, +minutes);

      this.malfunction.dateTime = date.toISOString();
    }
    this.dialogRef.close(this.malfunction);
  }

  cancel() {
    this.dialogRef.close();
  }
}
