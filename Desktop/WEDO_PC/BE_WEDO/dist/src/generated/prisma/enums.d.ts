export declare const Role: {
    readonly ADMIN: "ADMIN";
    readonly MEMBER: "MEMBER";
    readonly GUEST: "GUEST";
};
export type Role = (typeof Role)[keyof typeof Role];
export declare const ProjectStatus: {
    readonly ACTIVE: "ACTIVE";
    readonly COMPLETED: "COMPLETED";
    readonly ARCHIVED: "ARCHIVED";
};
export type ProjectStatus = (typeof ProjectStatus)[keyof typeof ProjectStatus];
export declare const TaskStatus: {
    readonly TODO: "TODO";
    readonly IN_PROGRESS: "IN_PROGRESS";
    readonly REVIEW: "REVIEW";
    readonly DONE: "DONE";
};
export type TaskStatus = (typeof TaskStatus)[keyof typeof TaskStatus];
