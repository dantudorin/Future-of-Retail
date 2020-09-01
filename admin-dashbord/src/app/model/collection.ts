import { CustomerTracker } from './customer-tracker';

export interface Collection {
    name : String;
    location : String;
    customerTracker? : CustomerTracker;
}