import { GroupUser } from './group-user';
import { GroupUrl } from './group-url';

export interface Group{
     groupId: number;
     groupName: string;
     groupContextName: string;
     groupContext: string;
     groupUrl: string;
     createdOn: string;
     users: GroupUser[];
     urls: GroupUrl[];
}
