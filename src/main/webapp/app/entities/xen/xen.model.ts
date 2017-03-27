import { Category } from '../category';
export class Xen {
    constructor(
        public id?: number,
        public title?: string,
        public description?: string,
        public isTask?: boolean,
        public isCompleated?: boolean,
        public category?: Category,
    ) {
        this.isTask = false;
        this.isCompleated = false;
    }
}
