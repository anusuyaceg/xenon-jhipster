import { Category } from '../category';
export class Xen {
    constructor(
        public id?: number,
        public title?: string,
        public description?: string,
        public isTask?: boolean,
        public isCompleted?: boolean,
        public category?: Category,
    ) {
        this.isTask = false;
        this.isCompleted = false;
    }
}
