import type { PaginationParams } from './paginationParams';

export interface OrderParams extends PaginationParams {
    stateId?: number;
}
