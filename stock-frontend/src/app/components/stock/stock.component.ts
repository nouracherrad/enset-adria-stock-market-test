import { Component, OnInit } from '@angular/core';
import { StockService } from '../../services/stock.service';
import { Stock } from '../../models/stock.model';

@Component({
  selector: 'app-stock',
  templateUrl: './stock.component.html',
  styleUrls: ['./stock.component.scss']
})
export class StockComponent implements OnInit {

  stocks: Stock[] = [];
  newStock: Stock = { date: '', openValue: 0, closeValue: 0, volume: 0, companyId: 0 };

  constructor(private stockService: StockService) {}

  ngOnInit(): void {
    this.loadStocks();
  }

  async loadStocks() {
    this.stockService.getAllStocks().subscribe(data => this.stocks = data);
  }

  addStock() {
    this.stockService.addStock(this.newStock).subscribe(() => {
      this.loadStocks();
      this.newStock = { date: '', openValue: 0, closeValue: 0, volume: 0, companyId: 0 };
    });
  }

  deleteStock(id: number) {
    this.stockService.deleteStock(id).subscribe(() => this.loadStocks());
  }
}
