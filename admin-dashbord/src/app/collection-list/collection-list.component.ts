import { Component, OnInit } from '@angular/core';
import { CollectionService } from '../service/collection.service';
import { Collection } from '../model/collection';
import { Observable, Subject } from 'rxjs';
import { NumberItems } from '../model/numberItems';
import { debounceTime, distinctUntilChanged, switchMap } from 'rxjs/operators';


@Component({
  selector: 'app-collection-list',
  templateUrl: './collection-list.component.html',
  styleUrls: ['./collection-list.component.css'],
  providers: [CollectionService]
})

export class CollectionListComponent implements OnInit {
  loading = false;
  errorMessage = '';
  emptyList : boolean;
  collections: Collection[];
  currentVal: string = "";
  results: any[] = [];
  searchText: string = "";
  private storeIdSubject = new Subject<string>();
  page: Number = 1
  items: Number = 1
  selectBy = "id"
  numberSelected: any = {};
  numberItems: NumberItems[];

  readonly stores$ = this.storeIdSubject.pipe(
    debounceTime(250),
    distinctUntilChanged(),
    switchMap(storeName => this.collectionService.getStoresBySearchedName(storeName))
  );

  constructor(private collectionService: CollectionService) { }

  ngOnInit(): void {
    this.loading = true;
    this.emptyList = false;
    this.errorMessage = '';
    this.numberItems = [
      { number: 1 },
      { number: 2 },
      { number: 3 },
      { number: 4 },
      { number: 5 },
    ];

    this.collectionService.getCollectionFromPage(this.page, this.items, this.selectBy)
      .subscribe(collections => {
        this.errorMessage = '';
        
        if(collections.length > 0) {
          this.collections = [...collections];
          console.log(collections);
        } else {
          this.emptyList = true;
        }
      
        this.loading = false;
      },
        error => {
          this.loading = false;
          this.errorMessage = error;
        });
  }

  onNumberSelected(val: NumberItems) {
    this.items = val.number;
  }
searchStore(val) {
    this.currentVal = val;
    this.collectionService.getStoresBySearchedName(val)
      .subscribe(collections => {
        this.errorMessage = '';

        if (collections.length > 0) {
          this.collections = [...collections];
          console.log(collections);
        } else {
          this.emptyList = true;
        }

        this.loading = false;
      },
        error => {
          this.loading = false;
          this.errorMessage = error;
        });
  }
}
