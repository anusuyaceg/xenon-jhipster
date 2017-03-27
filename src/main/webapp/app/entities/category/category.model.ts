import { Xen } from '../xen';
export class Category {
    constructor(
        public id?: number,
        public type?: string,
        public measurement?: string,
        public description?: string,
        public name?: string,
        public xen?: Xen,
    ) {
    }
}
