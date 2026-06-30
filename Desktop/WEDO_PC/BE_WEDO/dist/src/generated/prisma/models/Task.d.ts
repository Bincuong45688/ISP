import type * as runtime from "@prisma/client/runtime/client";
import type * as $Enums from "../enums.js";
import type * as Prisma from "../internal/prismaNamespace.js";
export type TaskModel = runtime.Types.Result.DefaultSelection<Prisma.$TaskPayload>;
export type AggregateTask = {
    _count: TaskCountAggregateOutputType | null;
    _min: TaskMinAggregateOutputType | null;
    _max: TaskMaxAggregateOutputType | null;
};
export type TaskMinAggregateOutputType = {
    id: string | null;
    title: string | null;
    description: string | null;
    status: $Enums.TaskStatus | null;
    dueDate: Date | null;
    projectId: string | null;
    workspaceId: string | null;
    assigneeId: string | null;
    createdAt: Date | null;
    updatedAt: Date | null;
};
export type TaskMaxAggregateOutputType = {
    id: string | null;
    title: string | null;
    description: string | null;
    status: $Enums.TaskStatus | null;
    dueDate: Date | null;
    projectId: string | null;
    workspaceId: string | null;
    assigneeId: string | null;
    createdAt: Date | null;
    updatedAt: Date | null;
};
export type TaskCountAggregateOutputType = {
    id: number;
    title: number;
    description: number;
    status: number;
    dueDate: number;
    projectId: number;
    workspaceId: number;
    assigneeId: number;
    createdAt: number;
    updatedAt: number;
    _all: number;
};
export type TaskMinAggregateInputType = {
    id?: true;
    title?: true;
    description?: true;
    status?: true;
    dueDate?: true;
    projectId?: true;
    workspaceId?: true;
    assigneeId?: true;
    createdAt?: true;
    updatedAt?: true;
};
export type TaskMaxAggregateInputType = {
    id?: true;
    title?: true;
    description?: true;
    status?: true;
    dueDate?: true;
    projectId?: true;
    workspaceId?: true;
    assigneeId?: true;
    createdAt?: true;
    updatedAt?: true;
};
export type TaskCountAggregateInputType = {
    id?: true;
    title?: true;
    description?: true;
    status?: true;
    dueDate?: true;
    projectId?: true;
    workspaceId?: true;
    assigneeId?: true;
    createdAt?: true;
    updatedAt?: true;
    _all?: true;
};
export type TaskAggregateArgs<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    where?: Prisma.TaskWhereInput;
    orderBy?: Prisma.TaskOrderByWithRelationInput | Prisma.TaskOrderByWithRelationInput[];
    cursor?: Prisma.TaskWhereUniqueInput;
    take?: number;
    skip?: number;
    _count?: true | TaskCountAggregateInputType;
    _min?: TaskMinAggregateInputType;
    _max?: TaskMaxAggregateInputType;
};
export type GetTaskAggregateType<T extends TaskAggregateArgs> = {
    [P in keyof T & keyof AggregateTask]: P extends '_count' | 'count' ? T[P] extends true ? number : Prisma.GetScalarType<T[P], AggregateTask[P]> : Prisma.GetScalarType<T[P], AggregateTask[P]>;
};
export type TaskGroupByArgs<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    where?: Prisma.TaskWhereInput;
    orderBy?: Prisma.TaskOrderByWithAggregationInput | Prisma.TaskOrderByWithAggregationInput[];
    by: Prisma.TaskScalarFieldEnum[] | Prisma.TaskScalarFieldEnum;
    having?: Prisma.TaskScalarWhereWithAggregatesInput;
    take?: number;
    skip?: number;
    _count?: TaskCountAggregateInputType | true;
    _min?: TaskMinAggregateInputType;
    _max?: TaskMaxAggregateInputType;
};
export type TaskGroupByOutputType = {
    id: string;
    title: string;
    description: string | null;
    status: $Enums.TaskStatus;
    dueDate: Date | null;
    projectId: string | null;
    workspaceId: string;
    assigneeId: string | null;
    createdAt: Date;
    updatedAt: Date;
    _count: TaskCountAggregateOutputType | null;
    _min: TaskMinAggregateOutputType | null;
    _max: TaskMaxAggregateOutputType | null;
};
export type GetTaskGroupByPayload<T extends TaskGroupByArgs> = Prisma.PrismaPromise<Array<Prisma.PickEnumerable<TaskGroupByOutputType, T['by']> & {
    [P in ((keyof T) & (keyof TaskGroupByOutputType))]: P extends '_count' ? T[P] extends boolean ? number : Prisma.GetScalarType<T[P], TaskGroupByOutputType[P]> : Prisma.GetScalarType<T[P], TaskGroupByOutputType[P]>;
}>>;
export type TaskWhereInput = {
    AND?: Prisma.TaskWhereInput | Prisma.TaskWhereInput[];
    OR?: Prisma.TaskWhereInput[];
    NOT?: Prisma.TaskWhereInput | Prisma.TaskWhereInput[];
    id?: Prisma.StringFilter<"Task"> | string;
    title?: Prisma.StringFilter<"Task"> | string;
    description?: Prisma.StringNullableFilter<"Task"> | string | null;
    status?: Prisma.EnumTaskStatusFilter<"Task"> | $Enums.TaskStatus;
    dueDate?: Prisma.DateTimeNullableFilter<"Task"> | Date | string | null;
    projectId?: Prisma.StringNullableFilter<"Task"> | string | null;
    workspaceId?: Prisma.StringFilter<"Task"> | string;
    assigneeId?: Prisma.StringNullableFilter<"Task"> | string | null;
    createdAt?: Prisma.DateTimeFilter<"Task"> | Date | string;
    updatedAt?: Prisma.DateTimeFilter<"Task"> | Date | string;
    project?: Prisma.XOR<Prisma.ProjectNullableScalarRelationFilter, Prisma.ProjectWhereInput> | null;
    workspace?: Prisma.XOR<Prisma.WorkspaceScalarRelationFilter, Prisma.WorkspaceWhereInput>;
    assignee?: Prisma.XOR<Prisma.UserNullableScalarRelationFilter, Prisma.UserWhereInput> | null;
};
export type TaskOrderByWithRelationInput = {
    id?: Prisma.SortOrder;
    title?: Prisma.SortOrder;
    description?: Prisma.SortOrderInput | Prisma.SortOrder;
    status?: Prisma.SortOrder;
    dueDate?: Prisma.SortOrderInput | Prisma.SortOrder;
    projectId?: Prisma.SortOrderInput | Prisma.SortOrder;
    workspaceId?: Prisma.SortOrder;
    assigneeId?: Prisma.SortOrderInput | Prisma.SortOrder;
    createdAt?: Prisma.SortOrder;
    updatedAt?: Prisma.SortOrder;
    project?: Prisma.ProjectOrderByWithRelationInput;
    workspace?: Prisma.WorkspaceOrderByWithRelationInput;
    assignee?: Prisma.UserOrderByWithRelationInput;
};
export type TaskWhereUniqueInput = Prisma.AtLeast<{
    id?: string;
    AND?: Prisma.TaskWhereInput | Prisma.TaskWhereInput[];
    OR?: Prisma.TaskWhereInput[];
    NOT?: Prisma.TaskWhereInput | Prisma.TaskWhereInput[];
    title?: Prisma.StringFilter<"Task"> | string;
    description?: Prisma.StringNullableFilter<"Task"> | string | null;
    status?: Prisma.EnumTaskStatusFilter<"Task"> | $Enums.TaskStatus;
    dueDate?: Prisma.DateTimeNullableFilter<"Task"> | Date | string | null;
    projectId?: Prisma.StringNullableFilter<"Task"> | string | null;
    workspaceId?: Prisma.StringFilter<"Task"> | string;
    assigneeId?: Prisma.StringNullableFilter<"Task"> | string | null;
    createdAt?: Prisma.DateTimeFilter<"Task"> | Date | string;
    updatedAt?: Prisma.DateTimeFilter<"Task"> | Date | string;
    project?: Prisma.XOR<Prisma.ProjectNullableScalarRelationFilter, Prisma.ProjectWhereInput> | null;
    workspace?: Prisma.XOR<Prisma.WorkspaceScalarRelationFilter, Prisma.WorkspaceWhereInput>;
    assignee?: Prisma.XOR<Prisma.UserNullableScalarRelationFilter, Prisma.UserWhereInput> | null;
}, "id">;
export type TaskOrderByWithAggregationInput = {
    id?: Prisma.SortOrder;
    title?: Prisma.SortOrder;
    description?: Prisma.SortOrderInput | Prisma.SortOrder;
    status?: Prisma.SortOrder;
    dueDate?: Prisma.SortOrderInput | Prisma.SortOrder;
    projectId?: Prisma.SortOrderInput | Prisma.SortOrder;
    workspaceId?: Prisma.SortOrder;
    assigneeId?: Prisma.SortOrderInput | Prisma.SortOrder;
    createdAt?: Prisma.SortOrder;
    updatedAt?: Prisma.SortOrder;
    _count?: Prisma.TaskCountOrderByAggregateInput;
    _max?: Prisma.TaskMaxOrderByAggregateInput;
    _min?: Prisma.TaskMinOrderByAggregateInput;
};
export type TaskScalarWhereWithAggregatesInput = {
    AND?: Prisma.TaskScalarWhereWithAggregatesInput | Prisma.TaskScalarWhereWithAggregatesInput[];
    OR?: Prisma.TaskScalarWhereWithAggregatesInput[];
    NOT?: Prisma.TaskScalarWhereWithAggregatesInput | Prisma.TaskScalarWhereWithAggregatesInput[];
    id?: Prisma.StringWithAggregatesFilter<"Task"> | string;
    title?: Prisma.StringWithAggregatesFilter<"Task"> | string;
    description?: Prisma.StringNullableWithAggregatesFilter<"Task"> | string | null;
    status?: Prisma.EnumTaskStatusWithAggregatesFilter<"Task"> | $Enums.TaskStatus;
    dueDate?: Prisma.DateTimeNullableWithAggregatesFilter<"Task"> | Date | string | null;
    projectId?: Prisma.StringNullableWithAggregatesFilter<"Task"> | string | null;
    workspaceId?: Prisma.StringWithAggregatesFilter<"Task"> | string;
    assigneeId?: Prisma.StringNullableWithAggregatesFilter<"Task"> | string | null;
    createdAt?: Prisma.DateTimeWithAggregatesFilter<"Task"> | Date | string;
    updatedAt?: Prisma.DateTimeWithAggregatesFilter<"Task"> | Date | string;
};
export type TaskCreateInput = {
    id?: string;
    title: string;
    description?: string | null;
    status?: $Enums.TaskStatus;
    dueDate?: Date | string | null;
    createdAt?: Date | string;
    updatedAt?: Date | string;
    project?: Prisma.ProjectCreateNestedOneWithoutTasksInput;
    workspace: Prisma.WorkspaceCreateNestedOneWithoutTasksInput;
    assignee?: Prisma.UserCreateNestedOneWithoutTasksInput;
};
export type TaskUncheckedCreateInput = {
    id?: string;
    title: string;
    description?: string | null;
    status?: $Enums.TaskStatus;
    dueDate?: Date | string | null;
    projectId?: string | null;
    workspaceId: string;
    assigneeId?: string | null;
    createdAt?: Date | string;
    updatedAt?: Date | string;
};
export type TaskUpdateInput = {
    id?: Prisma.StringFieldUpdateOperationsInput | string;
    title?: Prisma.StringFieldUpdateOperationsInput | string;
    description?: Prisma.NullableStringFieldUpdateOperationsInput | string | null;
    status?: Prisma.EnumTaskStatusFieldUpdateOperationsInput | $Enums.TaskStatus;
    dueDate?: Prisma.NullableDateTimeFieldUpdateOperationsInput | Date | string | null;
    createdAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    updatedAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    project?: Prisma.ProjectUpdateOneWithoutTasksNestedInput;
    workspace?: Prisma.WorkspaceUpdateOneRequiredWithoutTasksNestedInput;
    assignee?: Prisma.UserUpdateOneWithoutTasksNestedInput;
};
export type TaskUncheckedUpdateInput = {
    id?: Prisma.StringFieldUpdateOperationsInput | string;
    title?: Prisma.StringFieldUpdateOperationsInput | string;
    description?: Prisma.NullableStringFieldUpdateOperationsInput | string | null;
    status?: Prisma.EnumTaskStatusFieldUpdateOperationsInput | $Enums.TaskStatus;
    dueDate?: Prisma.NullableDateTimeFieldUpdateOperationsInput | Date | string | null;
    projectId?: Prisma.NullableStringFieldUpdateOperationsInput | string | null;
    workspaceId?: Prisma.StringFieldUpdateOperationsInput | string;
    assigneeId?: Prisma.NullableStringFieldUpdateOperationsInput | string | null;
    createdAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    updatedAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
};
export type TaskCreateManyInput = {
    id?: string;
    title: string;
    description?: string | null;
    status?: $Enums.TaskStatus;
    dueDate?: Date | string | null;
    projectId?: string | null;
    workspaceId: string;
    assigneeId?: string | null;
    createdAt?: Date | string;
    updatedAt?: Date | string;
};
export type TaskUpdateManyMutationInput = {
    id?: Prisma.StringFieldUpdateOperationsInput | string;
    title?: Prisma.StringFieldUpdateOperationsInput | string;
    description?: Prisma.NullableStringFieldUpdateOperationsInput | string | null;
    status?: Prisma.EnumTaskStatusFieldUpdateOperationsInput | $Enums.TaskStatus;
    dueDate?: Prisma.NullableDateTimeFieldUpdateOperationsInput | Date | string | null;
    createdAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    updatedAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
};
export type TaskUncheckedUpdateManyInput = {
    id?: Prisma.StringFieldUpdateOperationsInput | string;
    title?: Prisma.StringFieldUpdateOperationsInput | string;
    description?: Prisma.NullableStringFieldUpdateOperationsInput | string | null;
    status?: Prisma.EnumTaskStatusFieldUpdateOperationsInput | $Enums.TaskStatus;
    dueDate?: Prisma.NullableDateTimeFieldUpdateOperationsInput | Date | string | null;
    projectId?: Prisma.NullableStringFieldUpdateOperationsInput | string | null;
    workspaceId?: Prisma.StringFieldUpdateOperationsInput | string;
    assigneeId?: Prisma.NullableStringFieldUpdateOperationsInput | string | null;
    createdAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    updatedAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
};
export type TaskListRelationFilter = {
    every?: Prisma.TaskWhereInput;
    some?: Prisma.TaskWhereInput;
    none?: Prisma.TaskWhereInput;
};
export type TaskOrderByRelationAggregateInput = {
    _count?: Prisma.SortOrder;
};
export type TaskCountOrderByAggregateInput = {
    id?: Prisma.SortOrder;
    title?: Prisma.SortOrder;
    description?: Prisma.SortOrder;
    status?: Prisma.SortOrder;
    dueDate?: Prisma.SortOrder;
    projectId?: Prisma.SortOrder;
    workspaceId?: Prisma.SortOrder;
    assigneeId?: Prisma.SortOrder;
    createdAt?: Prisma.SortOrder;
    updatedAt?: Prisma.SortOrder;
};
export type TaskMaxOrderByAggregateInput = {
    id?: Prisma.SortOrder;
    title?: Prisma.SortOrder;
    description?: Prisma.SortOrder;
    status?: Prisma.SortOrder;
    dueDate?: Prisma.SortOrder;
    projectId?: Prisma.SortOrder;
    workspaceId?: Prisma.SortOrder;
    assigneeId?: Prisma.SortOrder;
    createdAt?: Prisma.SortOrder;
    updatedAt?: Prisma.SortOrder;
};
export type TaskMinOrderByAggregateInput = {
    id?: Prisma.SortOrder;
    title?: Prisma.SortOrder;
    description?: Prisma.SortOrder;
    status?: Prisma.SortOrder;
    dueDate?: Prisma.SortOrder;
    projectId?: Prisma.SortOrder;
    workspaceId?: Prisma.SortOrder;
    assigneeId?: Prisma.SortOrder;
    createdAt?: Prisma.SortOrder;
    updatedAt?: Prisma.SortOrder;
};
export type TaskCreateNestedManyWithoutAssigneeInput = {
    create?: Prisma.XOR<Prisma.TaskCreateWithoutAssigneeInput, Prisma.TaskUncheckedCreateWithoutAssigneeInput> | Prisma.TaskCreateWithoutAssigneeInput[] | Prisma.TaskUncheckedCreateWithoutAssigneeInput[];
    connectOrCreate?: Prisma.TaskCreateOrConnectWithoutAssigneeInput | Prisma.TaskCreateOrConnectWithoutAssigneeInput[];
    createMany?: Prisma.TaskCreateManyAssigneeInputEnvelope;
    connect?: Prisma.TaskWhereUniqueInput | Prisma.TaskWhereUniqueInput[];
};
export type TaskUncheckedCreateNestedManyWithoutAssigneeInput = {
    create?: Prisma.XOR<Prisma.TaskCreateWithoutAssigneeInput, Prisma.TaskUncheckedCreateWithoutAssigneeInput> | Prisma.TaskCreateWithoutAssigneeInput[] | Prisma.TaskUncheckedCreateWithoutAssigneeInput[];
    connectOrCreate?: Prisma.TaskCreateOrConnectWithoutAssigneeInput | Prisma.TaskCreateOrConnectWithoutAssigneeInput[];
    createMany?: Prisma.TaskCreateManyAssigneeInputEnvelope;
    connect?: Prisma.TaskWhereUniqueInput | Prisma.TaskWhereUniqueInput[];
};
export type TaskUpdateManyWithoutAssigneeNestedInput = {
    create?: Prisma.XOR<Prisma.TaskCreateWithoutAssigneeInput, Prisma.TaskUncheckedCreateWithoutAssigneeInput> | Prisma.TaskCreateWithoutAssigneeInput[] | Prisma.TaskUncheckedCreateWithoutAssigneeInput[];
    connectOrCreate?: Prisma.TaskCreateOrConnectWithoutAssigneeInput | Prisma.TaskCreateOrConnectWithoutAssigneeInput[];
    upsert?: Prisma.TaskUpsertWithWhereUniqueWithoutAssigneeInput | Prisma.TaskUpsertWithWhereUniqueWithoutAssigneeInput[];
    createMany?: Prisma.TaskCreateManyAssigneeInputEnvelope;
    set?: Prisma.TaskWhereUniqueInput | Prisma.TaskWhereUniqueInput[];
    disconnect?: Prisma.TaskWhereUniqueInput | Prisma.TaskWhereUniqueInput[];
    delete?: Prisma.TaskWhereUniqueInput | Prisma.TaskWhereUniqueInput[];
    connect?: Prisma.TaskWhereUniqueInput | Prisma.TaskWhereUniqueInput[];
    update?: Prisma.TaskUpdateWithWhereUniqueWithoutAssigneeInput | Prisma.TaskUpdateWithWhereUniqueWithoutAssigneeInput[];
    updateMany?: Prisma.TaskUpdateManyWithWhereWithoutAssigneeInput | Prisma.TaskUpdateManyWithWhereWithoutAssigneeInput[];
    deleteMany?: Prisma.TaskScalarWhereInput | Prisma.TaskScalarWhereInput[];
};
export type TaskUncheckedUpdateManyWithoutAssigneeNestedInput = {
    create?: Prisma.XOR<Prisma.TaskCreateWithoutAssigneeInput, Prisma.TaskUncheckedCreateWithoutAssigneeInput> | Prisma.TaskCreateWithoutAssigneeInput[] | Prisma.TaskUncheckedCreateWithoutAssigneeInput[];
    connectOrCreate?: Prisma.TaskCreateOrConnectWithoutAssigneeInput | Prisma.TaskCreateOrConnectWithoutAssigneeInput[];
    upsert?: Prisma.TaskUpsertWithWhereUniqueWithoutAssigneeInput | Prisma.TaskUpsertWithWhereUniqueWithoutAssigneeInput[];
    createMany?: Prisma.TaskCreateManyAssigneeInputEnvelope;
    set?: Prisma.TaskWhereUniqueInput | Prisma.TaskWhereUniqueInput[];
    disconnect?: Prisma.TaskWhereUniqueInput | Prisma.TaskWhereUniqueInput[];
    delete?: Prisma.TaskWhereUniqueInput | Prisma.TaskWhereUniqueInput[];
    connect?: Prisma.TaskWhereUniqueInput | Prisma.TaskWhereUniqueInput[];
    update?: Prisma.TaskUpdateWithWhereUniqueWithoutAssigneeInput | Prisma.TaskUpdateWithWhereUniqueWithoutAssigneeInput[];
    updateMany?: Prisma.TaskUpdateManyWithWhereWithoutAssigneeInput | Prisma.TaskUpdateManyWithWhereWithoutAssigneeInput[];
    deleteMany?: Prisma.TaskScalarWhereInput | Prisma.TaskScalarWhereInput[];
};
export type TaskCreateNestedManyWithoutWorkspaceInput = {
    create?: Prisma.XOR<Prisma.TaskCreateWithoutWorkspaceInput, Prisma.TaskUncheckedCreateWithoutWorkspaceInput> | Prisma.TaskCreateWithoutWorkspaceInput[] | Prisma.TaskUncheckedCreateWithoutWorkspaceInput[];
    connectOrCreate?: Prisma.TaskCreateOrConnectWithoutWorkspaceInput | Prisma.TaskCreateOrConnectWithoutWorkspaceInput[];
    createMany?: Prisma.TaskCreateManyWorkspaceInputEnvelope;
    connect?: Prisma.TaskWhereUniqueInput | Prisma.TaskWhereUniqueInput[];
};
export type TaskUncheckedCreateNestedManyWithoutWorkspaceInput = {
    create?: Prisma.XOR<Prisma.TaskCreateWithoutWorkspaceInput, Prisma.TaskUncheckedCreateWithoutWorkspaceInput> | Prisma.TaskCreateWithoutWorkspaceInput[] | Prisma.TaskUncheckedCreateWithoutWorkspaceInput[];
    connectOrCreate?: Prisma.TaskCreateOrConnectWithoutWorkspaceInput | Prisma.TaskCreateOrConnectWithoutWorkspaceInput[];
    createMany?: Prisma.TaskCreateManyWorkspaceInputEnvelope;
    connect?: Prisma.TaskWhereUniqueInput | Prisma.TaskWhereUniqueInput[];
};
export type TaskUpdateManyWithoutWorkspaceNestedInput = {
    create?: Prisma.XOR<Prisma.TaskCreateWithoutWorkspaceInput, Prisma.TaskUncheckedCreateWithoutWorkspaceInput> | Prisma.TaskCreateWithoutWorkspaceInput[] | Prisma.TaskUncheckedCreateWithoutWorkspaceInput[];
    connectOrCreate?: Prisma.TaskCreateOrConnectWithoutWorkspaceInput | Prisma.TaskCreateOrConnectWithoutWorkspaceInput[];
    upsert?: Prisma.TaskUpsertWithWhereUniqueWithoutWorkspaceInput | Prisma.TaskUpsertWithWhereUniqueWithoutWorkspaceInput[];
    createMany?: Prisma.TaskCreateManyWorkspaceInputEnvelope;
    set?: Prisma.TaskWhereUniqueInput | Prisma.TaskWhereUniqueInput[];
    disconnect?: Prisma.TaskWhereUniqueInput | Prisma.TaskWhereUniqueInput[];
    delete?: Prisma.TaskWhereUniqueInput | Prisma.TaskWhereUniqueInput[];
    connect?: Prisma.TaskWhereUniqueInput | Prisma.TaskWhereUniqueInput[];
    update?: Prisma.TaskUpdateWithWhereUniqueWithoutWorkspaceInput | Prisma.TaskUpdateWithWhereUniqueWithoutWorkspaceInput[];
    updateMany?: Prisma.TaskUpdateManyWithWhereWithoutWorkspaceInput | Prisma.TaskUpdateManyWithWhereWithoutWorkspaceInput[];
    deleteMany?: Prisma.TaskScalarWhereInput | Prisma.TaskScalarWhereInput[];
};
export type TaskUncheckedUpdateManyWithoutWorkspaceNestedInput = {
    create?: Prisma.XOR<Prisma.TaskCreateWithoutWorkspaceInput, Prisma.TaskUncheckedCreateWithoutWorkspaceInput> | Prisma.TaskCreateWithoutWorkspaceInput[] | Prisma.TaskUncheckedCreateWithoutWorkspaceInput[];
    connectOrCreate?: Prisma.TaskCreateOrConnectWithoutWorkspaceInput | Prisma.TaskCreateOrConnectWithoutWorkspaceInput[];
    upsert?: Prisma.TaskUpsertWithWhereUniqueWithoutWorkspaceInput | Prisma.TaskUpsertWithWhereUniqueWithoutWorkspaceInput[];
    createMany?: Prisma.TaskCreateManyWorkspaceInputEnvelope;
    set?: Prisma.TaskWhereUniqueInput | Prisma.TaskWhereUniqueInput[];
    disconnect?: Prisma.TaskWhereUniqueInput | Prisma.TaskWhereUniqueInput[];
    delete?: Prisma.TaskWhereUniqueInput | Prisma.TaskWhereUniqueInput[];
    connect?: Prisma.TaskWhereUniqueInput | Prisma.TaskWhereUniqueInput[];
    update?: Prisma.TaskUpdateWithWhereUniqueWithoutWorkspaceInput | Prisma.TaskUpdateWithWhereUniqueWithoutWorkspaceInput[];
    updateMany?: Prisma.TaskUpdateManyWithWhereWithoutWorkspaceInput | Prisma.TaskUpdateManyWithWhereWithoutWorkspaceInput[];
    deleteMany?: Prisma.TaskScalarWhereInput | Prisma.TaskScalarWhereInput[];
};
export type TaskCreateNestedManyWithoutProjectInput = {
    create?: Prisma.XOR<Prisma.TaskCreateWithoutProjectInput, Prisma.TaskUncheckedCreateWithoutProjectInput> | Prisma.TaskCreateWithoutProjectInput[] | Prisma.TaskUncheckedCreateWithoutProjectInput[];
    connectOrCreate?: Prisma.TaskCreateOrConnectWithoutProjectInput | Prisma.TaskCreateOrConnectWithoutProjectInput[];
    createMany?: Prisma.TaskCreateManyProjectInputEnvelope;
    connect?: Prisma.TaskWhereUniqueInput | Prisma.TaskWhereUniqueInput[];
};
export type TaskUncheckedCreateNestedManyWithoutProjectInput = {
    create?: Prisma.XOR<Prisma.TaskCreateWithoutProjectInput, Prisma.TaskUncheckedCreateWithoutProjectInput> | Prisma.TaskCreateWithoutProjectInput[] | Prisma.TaskUncheckedCreateWithoutProjectInput[];
    connectOrCreate?: Prisma.TaskCreateOrConnectWithoutProjectInput | Prisma.TaskCreateOrConnectWithoutProjectInput[];
    createMany?: Prisma.TaskCreateManyProjectInputEnvelope;
    connect?: Prisma.TaskWhereUniqueInput | Prisma.TaskWhereUniqueInput[];
};
export type TaskUpdateManyWithoutProjectNestedInput = {
    create?: Prisma.XOR<Prisma.TaskCreateWithoutProjectInput, Prisma.TaskUncheckedCreateWithoutProjectInput> | Prisma.TaskCreateWithoutProjectInput[] | Prisma.TaskUncheckedCreateWithoutProjectInput[];
    connectOrCreate?: Prisma.TaskCreateOrConnectWithoutProjectInput | Prisma.TaskCreateOrConnectWithoutProjectInput[];
    upsert?: Prisma.TaskUpsertWithWhereUniqueWithoutProjectInput | Prisma.TaskUpsertWithWhereUniqueWithoutProjectInput[];
    createMany?: Prisma.TaskCreateManyProjectInputEnvelope;
    set?: Prisma.TaskWhereUniqueInput | Prisma.TaskWhereUniqueInput[];
    disconnect?: Prisma.TaskWhereUniqueInput | Prisma.TaskWhereUniqueInput[];
    delete?: Prisma.TaskWhereUniqueInput | Prisma.TaskWhereUniqueInput[];
    connect?: Prisma.TaskWhereUniqueInput | Prisma.TaskWhereUniqueInput[];
    update?: Prisma.TaskUpdateWithWhereUniqueWithoutProjectInput | Prisma.TaskUpdateWithWhereUniqueWithoutProjectInput[];
    updateMany?: Prisma.TaskUpdateManyWithWhereWithoutProjectInput | Prisma.TaskUpdateManyWithWhereWithoutProjectInput[];
    deleteMany?: Prisma.TaskScalarWhereInput | Prisma.TaskScalarWhereInput[];
};
export type TaskUncheckedUpdateManyWithoutProjectNestedInput = {
    create?: Prisma.XOR<Prisma.TaskCreateWithoutProjectInput, Prisma.TaskUncheckedCreateWithoutProjectInput> | Prisma.TaskCreateWithoutProjectInput[] | Prisma.TaskUncheckedCreateWithoutProjectInput[];
    connectOrCreate?: Prisma.TaskCreateOrConnectWithoutProjectInput | Prisma.TaskCreateOrConnectWithoutProjectInput[];
    upsert?: Prisma.TaskUpsertWithWhereUniqueWithoutProjectInput | Prisma.TaskUpsertWithWhereUniqueWithoutProjectInput[];
    createMany?: Prisma.TaskCreateManyProjectInputEnvelope;
    set?: Prisma.TaskWhereUniqueInput | Prisma.TaskWhereUniqueInput[];
    disconnect?: Prisma.TaskWhereUniqueInput | Prisma.TaskWhereUniqueInput[];
    delete?: Prisma.TaskWhereUniqueInput | Prisma.TaskWhereUniqueInput[];
    connect?: Prisma.TaskWhereUniqueInput | Prisma.TaskWhereUniqueInput[];
    update?: Prisma.TaskUpdateWithWhereUniqueWithoutProjectInput | Prisma.TaskUpdateWithWhereUniqueWithoutProjectInput[];
    updateMany?: Prisma.TaskUpdateManyWithWhereWithoutProjectInput | Prisma.TaskUpdateManyWithWhereWithoutProjectInput[];
    deleteMany?: Prisma.TaskScalarWhereInput | Prisma.TaskScalarWhereInput[];
};
export type EnumTaskStatusFieldUpdateOperationsInput = {
    set?: $Enums.TaskStatus;
};
export type NullableDateTimeFieldUpdateOperationsInput = {
    set?: Date | string | null;
};
export type TaskCreateWithoutAssigneeInput = {
    id?: string;
    title: string;
    description?: string | null;
    status?: $Enums.TaskStatus;
    dueDate?: Date | string | null;
    createdAt?: Date | string;
    updatedAt?: Date | string;
    project?: Prisma.ProjectCreateNestedOneWithoutTasksInput;
    workspace: Prisma.WorkspaceCreateNestedOneWithoutTasksInput;
};
export type TaskUncheckedCreateWithoutAssigneeInput = {
    id?: string;
    title: string;
    description?: string | null;
    status?: $Enums.TaskStatus;
    dueDate?: Date | string | null;
    projectId?: string | null;
    workspaceId: string;
    createdAt?: Date | string;
    updatedAt?: Date | string;
};
export type TaskCreateOrConnectWithoutAssigneeInput = {
    where: Prisma.TaskWhereUniqueInput;
    create: Prisma.XOR<Prisma.TaskCreateWithoutAssigneeInput, Prisma.TaskUncheckedCreateWithoutAssigneeInput>;
};
export type TaskCreateManyAssigneeInputEnvelope = {
    data: Prisma.TaskCreateManyAssigneeInput | Prisma.TaskCreateManyAssigneeInput[];
    skipDuplicates?: boolean;
};
export type TaskUpsertWithWhereUniqueWithoutAssigneeInput = {
    where: Prisma.TaskWhereUniqueInput;
    update: Prisma.XOR<Prisma.TaskUpdateWithoutAssigneeInput, Prisma.TaskUncheckedUpdateWithoutAssigneeInput>;
    create: Prisma.XOR<Prisma.TaskCreateWithoutAssigneeInput, Prisma.TaskUncheckedCreateWithoutAssigneeInput>;
};
export type TaskUpdateWithWhereUniqueWithoutAssigneeInput = {
    where: Prisma.TaskWhereUniqueInput;
    data: Prisma.XOR<Prisma.TaskUpdateWithoutAssigneeInput, Prisma.TaskUncheckedUpdateWithoutAssigneeInput>;
};
export type TaskUpdateManyWithWhereWithoutAssigneeInput = {
    where: Prisma.TaskScalarWhereInput;
    data: Prisma.XOR<Prisma.TaskUpdateManyMutationInput, Prisma.TaskUncheckedUpdateManyWithoutAssigneeInput>;
};
export type TaskScalarWhereInput = {
    AND?: Prisma.TaskScalarWhereInput | Prisma.TaskScalarWhereInput[];
    OR?: Prisma.TaskScalarWhereInput[];
    NOT?: Prisma.TaskScalarWhereInput | Prisma.TaskScalarWhereInput[];
    id?: Prisma.StringFilter<"Task"> | string;
    title?: Prisma.StringFilter<"Task"> | string;
    description?: Prisma.StringNullableFilter<"Task"> | string | null;
    status?: Prisma.EnumTaskStatusFilter<"Task"> | $Enums.TaskStatus;
    dueDate?: Prisma.DateTimeNullableFilter<"Task"> | Date | string | null;
    projectId?: Prisma.StringNullableFilter<"Task"> | string | null;
    workspaceId?: Prisma.StringFilter<"Task"> | string;
    assigneeId?: Prisma.StringNullableFilter<"Task"> | string | null;
    createdAt?: Prisma.DateTimeFilter<"Task"> | Date | string;
    updatedAt?: Prisma.DateTimeFilter<"Task"> | Date | string;
};
export type TaskCreateWithoutWorkspaceInput = {
    id?: string;
    title: string;
    description?: string | null;
    status?: $Enums.TaskStatus;
    dueDate?: Date | string | null;
    createdAt?: Date | string;
    updatedAt?: Date | string;
    project?: Prisma.ProjectCreateNestedOneWithoutTasksInput;
    assignee?: Prisma.UserCreateNestedOneWithoutTasksInput;
};
export type TaskUncheckedCreateWithoutWorkspaceInput = {
    id?: string;
    title: string;
    description?: string | null;
    status?: $Enums.TaskStatus;
    dueDate?: Date | string | null;
    projectId?: string | null;
    assigneeId?: string | null;
    createdAt?: Date | string;
    updatedAt?: Date | string;
};
export type TaskCreateOrConnectWithoutWorkspaceInput = {
    where: Prisma.TaskWhereUniqueInput;
    create: Prisma.XOR<Prisma.TaskCreateWithoutWorkspaceInput, Prisma.TaskUncheckedCreateWithoutWorkspaceInput>;
};
export type TaskCreateManyWorkspaceInputEnvelope = {
    data: Prisma.TaskCreateManyWorkspaceInput | Prisma.TaskCreateManyWorkspaceInput[];
    skipDuplicates?: boolean;
};
export type TaskUpsertWithWhereUniqueWithoutWorkspaceInput = {
    where: Prisma.TaskWhereUniqueInput;
    update: Prisma.XOR<Prisma.TaskUpdateWithoutWorkspaceInput, Prisma.TaskUncheckedUpdateWithoutWorkspaceInput>;
    create: Prisma.XOR<Prisma.TaskCreateWithoutWorkspaceInput, Prisma.TaskUncheckedCreateWithoutWorkspaceInput>;
};
export type TaskUpdateWithWhereUniqueWithoutWorkspaceInput = {
    where: Prisma.TaskWhereUniqueInput;
    data: Prisma.XOR<Prisma.TaskUpdateWithoutWorkspaceInput, Prisma.TaskUncheckedUpdateWithoutWorkspaceInput>;
};
export type TaskUpdateManyWithWhereWithoutWorkspaceInput = {
    where: Prisma.TaskScalarWhereInput;
    data: Prisma.XOR<Prisma.TaskUpdateManyMutationInput, Prisma.TaskUncheckedUpdateManyWithoutWorkspaceInput>;
};
export type TaskCreateWithoutProjectInput = {
    id?: string;
    title: string;
    description?: string | null;
    status?: $Enums.TaskStatus;
    dueDate?: Date | string | null;
    createdAt?: Date | string;
    updatedAt?: Date | string;
    workspace: Prisma.WorkspaceCreateNestedOneWithoutTasksInput;
    assignee?: Prisma.UserCreateNestedOneWithoutTasksInput;
};
export type TaskUncheckedCreateWithoutProjectInput = {
    id?: string;
    title: string;
    description?: string | null;
    status?: $Enums.TaskStatus;
    dueDate?: Date | string | null;
    workspaceId: string;
    assigneeId?: string | null;
    createdAt?: Date | string;
    updatedAt?: Date | string;
};
export type TaskCreateOrConnectWithoutProjectInput = {
    where: Prisma.TaskWhereUniqueInput;
    create: Prisma.XOR<Prisma.TaskCreateWithoutProjectInput, Prisma.TaskUncheckedCreateWithoutProjectInput>;
};
export type TaskCreateManyProjectInputEnvelope = {
    data: Prisma.TaskCreateManyProjectInput | Prisma.TaskCreateManyProjectInput[];
    skipDuplicates?: boolean;
};
export type TaskUpsertWithWhereUniqueWithoutProjectInput = {
    where: Prisma.TaskWhereUniqueInput;
    update: Prisma.XOR<Prisma.TaskUpdateWithoutProjectInput, Prisma.TaskUncheckedUpdateWithoutProjectInput>;
    create: Prisma.XOR<Prisma.TaskCreateWithoutProjectInput, Prisma.TaskUncheckedCreateWithoutProjectInput>;
};
export type TaskUpdateWithWhereUniqueWithoutProjectInput = {
    where: Prisma.TaskWhereUniqueInput;
    data: Prisma.XOR<Prisma.TaskUpdateWithoutProjectInput, Prisma.TaskUncheckedUpdateWithoutProjectInput>;
};
export type TaskUpdateManyWithWhereWithoutProjectInput = {
    where: Prisma.TaskScalarWhereInput;
    data: Prisma.XOR<Prisma.TaskUpdateManyMutationInput, Prisma.TaskUncheckedUpdateManyWithoutProjectInput>;
};
export type TaskCreateManyAssigneeInput = {
    id?: string;
    title: string;
    description?: string | null;
    status?: $Enums.TaskStatus;
    dueDate?: Date | string | null;
    projectId?: string | null;
    workspaceId: string;
    createdAt?: Date | string;
    updatedAt?: Date | string;
};
export type TaskUpdateWithoutAssigneeInput = {
    id?: Prisma.StringFieldUpdateOperationsInput | string;
    title?: Prisma.StringFieldUpdateOperationsInput | string;
    description?: Prisma.NullableStringFieldUpdateOperationsInput | string | null;
    status?: Prisma.EnumTaskStatusFieldUpdateOperationsInput | $Enums.TaskStatus;
    dueDate?: Prisma.NullableDateTimeFieldUpdateOperationsInput | Date | string | null;
    createdAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    updatedAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    project?: Prisma.ProjectUpdateOneWithoutTasksNestedInput;
    workspace?: Prisma.WorkspaceUpdateOneRequiredWithoutTasksNestedInput;
};
export type TaskUncheckedUpdateWithoutAssigneeInput = {
    id?: Prisma.StringFieldUpdateOperationsInput | string;
    title?: Prisma.StringFieldUpdateOperationsInput | string;
    description?: Prisma.NullableStringFieldUpdateOperationsInput | string | null;
    status?: Prisma.EnumTaskStatusFieldUpdateOperationsInput | $Enums.TaskStatus;
    dueDate?: Prisma.NullableDateTimeFieldUpdateOperationsInput | Date | string | null;
    projectId?: Prisma.NullableStringFieldUpdateOperationsInput | string | null;
    workspaceId?: Prisma.StringFieldUpdateOperationsInput | string;
    createdAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    updatedAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
};
export type TaskUncheckedUpdateManyWithoutAssigneeInput = {
    id?: Prisma.StringFieldUpdateOperationsInput | string;
    title?: Prisma.StringFieldUpdateOperationsInput | string;
    description?: Prisma.NullableStringFieldUpdateOperationsInput | string | null;
    status?: Prisma.EnumTaskStatusFieldUpdateOperationsInput | $Enums.TaskStatus;
    dueDate?: Prisma.NullableDateTimeFieldUpdateOperationsInput | Date | string | null;
    projectId?: Prisma.NullableStringFieldUpdateOperationsInput | string | null;
    workspaceId?: Prisma.StringFieldUpdateOperationsInput | string;
    createdAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    updatedAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
};
export type TaskCreateManyWorkspaceInput = {
    id?: string;
    title: string;
    description?: string | null;
    status?: $Enums.TaskStatus;
    dueDate?: Date | string | null;
    projectId?: string | null;
    assigneeId?: string | null;
    createdAt?: Date | string;
    updatedAt?: Date | string;
};
export type TaskUpdateWithoutWorkspaceInput = {
    id?: Prisma.StringFieldUpdateOperationsInput | string;
    title?: Prisma.StringFieldUpdateOperationsInput | string;
    description?: Prisma.NullableStringFieldUpdateOperationsInput | string | null;
    status?: Prisma.EnumTaskStatusFieldUpdateOperationsInput | $Enums.TaskStatus;
    dueDate?: Prisma.NullableDateTimeFieldUpdateOperationsInput | Date | string | null;
    createdAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    updatedAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    project?: Prisma.ProjectUpdateOneWithoutTasksNestedInput;
    assignee?: Prisma.UserUpdateOneWithoutTasksNestedInput;
};
export type TaskUncheckedUpdateWithoutWorkspaceInput = {
    id?: Prisma.StringFieldUpdateOperationsInput | string;
    title?: Prisma.StringFieldUpdateOperationsInput | string;
    description?: Prisma.NullableStringFieldUpdateOperationsInput | string | null;
    status?: Prisma.EnumTaskStatusFieldUpdateOperationsInput | $Enums.TaskStatus;
    dueDate?: Prisma.NullableDateTimeFieldUpdateOperationsInput | Date | string | null;
    projectId?: Prisma.NullableStringFieldUpdateOperationsInput | string | null;
    assigneeId?: Prisma.NullableStringFieldUpdateOperationsInput | string | null;
    createdAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    updatedAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
};
export type TaskUncheckedUpdateManyWithoutWorkspaceInput = {
    id?: Prisma.StringFieldUpdateOperationsInput | string;
    title?: Prisma.StringFieldUpdateOperationsInput | string;
    description?: Prisma.NullableStringFieldUpdateOperationsInput | string | null;
    status?: Prisma.EnumTaskStatusFieldUpdateOperationsInput | $Enums.TaskStatus;
    dueDate?: Prisma.NullableDateTimeFieldUpdateOperationsInput | Date | string | null;
    projectId?: Prisma.NullableStringFieldUpdateOperationsInput | string | null;
    assigneeId?: Prisma.NullableStringFieldUpdateOperationsInput | string | null;
    createdAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    updatedAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
};
export type TaskCreateManyProjectInput = {
    id?: string;
    title: string;
    description?: string | null;
    status?: $Enums.TaskStatus;
    dueDate?: Date | string | null;
    workspaceId: string;
    assigneeId?: string | null;
    createdAt?: Date | string;
    updatedAt?: Date | string;
};
export type TaskUpdateWithoutProjectInput = {
    id?: Prisma.StringFieldUpdateOperationsInput | string;
    title?: Prisma.StringFieldUpdateOperationsInput | string;
    description?: Prisma.NullableStringFieldUpdateOperationsInput | string | null;
    status?: Prisma.EnumTaskStatusFieldUpdateOperationsInput | $Enums.TaskStatus;
    dueDate?: Prisma.NullableDateTimeFieldUpdateOperationsInput | Date | string | null;
    createdAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    updatedAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    workspace?: Prisma.WorkspaceUpdateOneRequiredWithoutTasksNestedInput;
    assignee?: Prisma.UserUpdateOneWithoutTasksNestedInput;
};
export type TaskUncheckedUpdateWithoutProjectInput = {
    id?: Prisma.StringFieldUpdateOperationsInput | string;
    title?: Prisma.StringFieldUpdateOperationsInput | string;
    description?: Prisma.NullableStringFieldUpdateOperationsInput | string | null;
    status?: Prisma.EnumTaskStatusFieldUpdateOperationsInput | $Enums.TaskStatus;
    dueDate?: Prisma.NullableDateTimeFieldUpdateOperationsInput | Date | string | null;
    workspaceId?: Prisma.StringFieldUpdateOperationsInput | string;
    assigneeId?: Prisma.NullableStringFieldUpdateOperationsInput | string | null;
    createdAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    updatedAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
};
export type TaskUncheckedUpdateManyWithoutProjectInput = {
    id?: Prisma.StringFieldUpdateOperationsInput | string;
    title?: Prisma.StringFieldUpdateOperationsInput | string;
    description?: Prisma.NullableStringFieldUpdateOperationsInput | string | null;
    status?: Prisma.EnumTaskStatusFieldUpdateOperationsInput | $Enums.TaskStatus;
    dueDate?: Prisma.NullableDateTimeFieldUpdateOperationsInput | Date | string | null;
    workspaceId?: Prisma.StringFieldUpdateOperationsInput | string;
    assigneeId?: Prisma.NullableStringFieldUpdateOperationsInput | string | null;
    createdAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    updatedAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
};
export type TaskSelect<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = runtime.Types.Extensions.GetSelect<{
    id?: boolean;
    title?: boolean;
    description?: boolean;
    status?: boolean;
    dueDate?: boolean;
    projectId?: boolean;
    workspaceId?: boolean;
    assigneeId?: boolean;
    createdAt?: boolean;
    updatedAt?: boolean;
    project?: boolean | Prisma.Task$projectArgs<ExtArgs>;
    workspace?: boolean | Prisma.WorkspaceDefaultArgs<ExtArgs>;
    assignee?: boolean | Prisma.Task$assigneeArgs<ExtArgs>;
}, ExtArgs["result"]["task"]>;
export type TaskSelectCreateManyAndReturn<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = runtime.Types.Extensions.GetSelect<{
    id?: boolean;
    title?: boolean;
    description?: boolean;
    status?: boolean;
    dueDate?: boolean;
    projectId?: boolean;
    workspaceId?: boolean;
    assigneeId?: boolean;
    createdAt?: boolean;
    updatedAt?: boolean;
    project?: boolean | Prisma.Task$projectArgs<ExtArgs>;
    workspace?: boolean | Prisma.WorkspaceDefaultArgs<ExtArgs>;
    assignee?: boolean | Prisma.Task$assigneeArgs<ExtArgs>;
}, ExtArgs["result"]["task"]>;
export type TaskSelectUpdateManyAndReturn<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = runtime.Types.Extensions.GetSelect<{
    id?: boolean;
    title?: boolean;
    description?: boolean;
    status?: boolean;
    dueDate?: boolean;
    projectId?: boolean;
    workspaceId?: boolean;
    assigneeId?: boolean;
    createdAt?: boolean;
    updatedAt?: boolean;
    project?: boolean | Prisma.Task$projectArgs<ExtArgs>;
    workspace?: boolean | Prisma.WorkspaceDefaultArgs<ExtArgs>;
    assignee?: boolean | Prisma.Task$assigneeArgs<ExtArgs>;
}, ExtArgs["result"]["task"]>;
export type TaskSelectScalar = {
    id?: boolean;
    title?: boolean;
    description?: boolean;
    status?: boolean;
    dueDate?: boolean;
    projectId?: boolean;
    workspaceId?: boolean;
    assigneeId?: boolean;
    createdAt?: boolean;
    updatedAt?: boolean;
};
export type TaskOmit<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = runtime.Types.Extensions.GetOmit<"id" | "title" | "description" | "status" | "dueDate" | "projectId" | "workspaceId" | "assigneeId" | "createdAt" | "updatedAt", ExtArgs["result"]["task"]>;
export type TaskInclude<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    project?: boolean | Prisma.Task$projectArgs<ExtArgs>;
    workspace?: boolean | Prisma.WorkspaceDefaultArgs<ExtArgs>;
    assignee?: boolean | Prisma.Task$assigneeArgs<ExtArgs>;
};
export type TaskIncludeCreateManyAndReturn<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    project?: boolean | Prisma.Task$projectArgs<ExtArgs>;
    workspace?: boolean | Prisma.WorkspaceDefaultArgs<ExtArgs>;
    assignee?: boolean | Prisma.Task$assigneeArgs<ExtArgs>;
};
export type TaskIncludeUpdateManyAndReturn<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    project?: boolean | Prisma.Task$projectArgs<ExtArgs>;
    workspace?: boolean | Prisma.WorkspaceDefaultArgs<ExtArgs>;
    assignee?: boolean | Prisma.Task$assigneeArgs<ExtArgs>;
};
export type $TaskPayload<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    name: "Task";
    objects: {
        project: Prisma.$ProjectPayload<ExtArgs> | null;
        workspace: Prisma.$WorkspacePayload<ExtArgs>;
        assignee: Prisma.$UserPayload<ExtArgs> | null;
    };
    scalars: runtime.Types.Extensions.GetPayloadResult<{
        id: string;
        title: string;
        description: string | null;
        status: $Enums.TaskStatus;
        dueDate: Date | null;
        projectId: string | null;
        workspaceId: string;
        assigneeId: string | null;
        createdAt: Date;
        updatedAt: Date;
    }, ExtArgs["result"]["task"]>;
    composites: {};
};
export type TaskGetPayload<S extends boolean | null | undefined | TaskDefaultArgs> = runtime.Types.Result.GetResult<Prisma.$TaskPayload, S>;
export type TaskCountArgs<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = Omit<TaskFindManyArgs, 'select' | 'include' | 'distinct' | 'omit'> & {
    select?: TaskCountAggregateInputType | true;
};
export interface TaskDelegate<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs, GlobalOmitOptions = {}> {
    [K: symbol]: {
        types: Prisma.TypeMap<ExtArgs>['model']['Task'];
        meta: {
            name: 'Task';
        };
    };
    findUnique<T extends TaskFindUniqueArgs>(args: Prisma.SelectSubset<T, TaskFindUniqueArgs<ExtArgs>>): Prisma.Prisma__TaskClient<runtime.Types.Result.GetResult<Prisma.$TaskPayload<ExtArgs>, T, "findUnique", GlobalOmitOptions> | null, null, ExtArgs, GlobalOmitOptions>;
    findUniqueOrThrow<T extends TaskFindUniqueOrThrowArgs>(args: Prisma.SelectSubset<T, TaskFindUniqueOrThrowArgs<ExtArgs>>): Prisma.Prisma__TaskClient<runtime.Types.Result.GetResult<Prisma.$TaskPayload<ExtArgs>, T, "findUniqueOrThrow", GlobalOmitOptions>, never, ExtArgs, GlobalOmitOptions>;
    findFirst<T extends TaskFindFirstArgs>(args?: Prisma.SelectSubset<T, TaskFindFirstArgs<ExtArgs>>): Prisma.Prisma__TaskClient<runtime.Types.Result.GetResult<Prisma.$TaskPayload<ExtArgs>, T, "findFirst", GlobalOmitOptions> | null, null, ExtArgs, GlobalOmitOptions>;
    findFirstOrThrow<T extends TaskFindFirstOrThrowArgs>(args?: Prisma.SelectSubset<T, TaskFindFirstOrThrowArgs<ExtArgs>>): Prisma.Prisma__TaskClient<runtime.Types.Result.GetResult<Prisma.$TaskPayload<ExtArgs>, T, "findFirstOrThrow", GlobalOmitOptions>, never, ExtArgs, GlobalOmitOptions>;
    findMany<T extends TaskFindManyArgs>(args?: Prisma.SelectSubset<T, TaskFindManyArgs<ExtArgs>>): Prisma.PrismaPromise<runtime.Types.Result.GetResult<Prisma.$TaskPayload<ExtArgs>, T, "findMany", GlobalOmitOptions>>;
    create<T extends TaskCreateArgs>(args: Prisma.SelectSubset<T, TaskCreateArgs<ExtArgs>>): Prisma.Prisma__TaskClient<runtime.Types.Result.GetResult<Prisma.$TaskPayload<ExtArgs>, T, "create", GlobalOmitOptions>, never, ExtArgs, GlobalOmitOptions>;
    createMany<T extends TaskCreateManyArgs>(args?: Prisma.SelectSubset<T, TaskCreateManyArgs<ExtArgs>>): Prisma.PrismaPromise<Prisma.BatchPayload>;
    createManyAndReturn<T extends TaskCreateManyAndReturnArgs>(args?: Prisma.SelectSubset<T, TaskCreateManyAndReturnArgs<ExtArgs>>): Prisma.PrismaPromise<runtime.Types.Result.GetResult<Prisma.$TaskPayload<ExtArgs>, T, "createManyAndReturn", GlobalOmitOptions>>;
    delete<T extends TaskDeleteArgs>(args: Prisma.SelectSubset<T, TaskDeleteArgs<ExtArgs>>): Prisma.Prisma__TaskClient<runtime.Types.Result.GetResult<Prisma.$TaskPayload<ExtArgs>, T, "delete", GlobalOmitOptions>, never, ExtArgs, GlobalOmitOptions>;
    update<T extends TaskUpdateArgs>(args: Prisma.SelectSubset<T, TaskUpdateArgs<ExtArgs>>): Prisma.Prisma__TaskClient<runtime.Types.Result.GetResult<Prisma.$TaskPayload<ExtArgs>, T, "update", GlobalOmitOptions>, never, ExtArgs, GlobalOmitOptions>;
    deleteMany<T extends TaskDeleteManyArgs>(args?: Prisma.SelectSubset<T, TaskDeleteManyArgs<ExtArgs>>): Prisma.PrismaPromise<Prisma.BatchPayload>;
    updateMany<T extends TaskUpdateManyArgs>(args: Prisma.SelectSubset<T, TaskUpdateManyArgs<ExtArgs>>): Prisma.PrismaPromise<Prisma.BatchPayload>;
    updateManyAndReturn<T extends TaskUpdateManyAndReturnArgs>(args: Prisma.SelectSubset<T, TaskUpdateManyAndReturnArgs<ExtArgs>>): Prisma.PrismaPromise<runtime.Types.Result.GetResult<Prisma.$TaskPayload<ExtArgs>, T, "updateManyAndReturn", GlobalOmitOptions>>;
    upsert<T extends TaskUpsertArgs>(args: Prisma.SelectSubset<T, TaskUpsertArgs<ExtArgs>>): Prisma.Prisma__TaskClient<runtime.Types.Result.GetResult<Prisma.$TaskPayload<ExtArgs>, T, "upsert", GlobalOmitOptions>, never, ExtArgs, GlobalOmitOptions>;
    count<T extends TaskCountArgs>(args?: Prisma.Subset<T, TaskCountArgs>): Prisma.PrismaPromise<T extends runtime.Types.Utils.Record<'select', any> ? T['select'] extends true ? number : Prisma.GetScalarType<T['select'], TaskCountAggregateOutputType> : number>;
    aggregate<T extends TaskAggregateArgs>(args: Prisma.Subset<T, TaskAggregateArgs>): Prisma.PrismaPromise<GetTaskAggregateType<T>>;
    groupBy<T extends TaskGroupByArgs, HasSelectOrTake extends Prisma.Or<Prisma.Extends<'skip', Prisma.Keys<T>>, Prisma.Extends<'take', Prisma.Keys<T>>>, OrderByArg extends Prisma.True extends HasSelectOrTake ? {
        orderBy: TaskGroupByArgs['orderBy'];
    } : {
        orderBy?: TaskGroupByArgs['orderBy'];
    }, OrderFields extends Prisma.ExcludeUnderscoreKeys<Prisma.Keys<Prisma.MaybeTupleToUnion<T['orderBy']>>>, ByFields extends Prisma.MaybeTupleToUnion<T['by']>, ByValid extends Prisma.Has<ByFields, OrderFields>, HavingFields extends Prisma.GetHavingFields<T['having']>, HavingValid extends Prisma.Has<ByFields, HavingFields>, ByEmpty extends T['by'] extends never[] ? Prisma.True : Prisma.False, InputErrors extends ByEmpty extends Prisma.True ? `Error: "by" must not be empty.` : HavingValid extends Prisma.False ? {
        [P in HavingFields]: P extends ByFields ? never : P extends string ? `Error: Field "${P}" used in "having" needs to be provided in "by".` : [
            Error,
            'Field ',
            P,
            ` in "having" needs to be provided in "by"`
        ];
    }[HavingFields] : 'take' extends Prisma.Keys<T> ? 'orderBy' extends Prisma.Keys<T> ? ByValid extends Prisma.True ? {} : {
        [P in OrderFields]: P extends ByFields ? never : `Error: Field "${P}" in "orderBy" needs to be provided in "by"`;
    }[OrderFields] : 'Error: If you provide "take", you also need to provide "orderBy"' : 'skip' extends Prisma.Keys<T> ? 'orderBy' extends Prisma.Keys<T> ? ByValid extends Prisma.True ? {} : {
        [P in OrderFields]: P extends ByFields ? never : `Error: Field "${P}" in "orderBy" needs to be provided in "by"`;
    }[OrderFields] : 'Error: If you provide "skip", you also need to provide "orderBy"' : ByValid extends Prisma.True ? {} : {
        [P in OrderFields]: P extends ByFields ? never : `Error: Field "${P}" in "orderBy" needs to be provided in "by"`;
    }[OrderFields]>(args: Prisma.SubsetIntersection<T, TaskGroupByArgs, OrderByArg> & InputErrors): {} extends InputErrors ? GetTaskGroupByPayload<T> : Prisma.PrismaPromise<InputErrors>;
    readonly fields: TaskFieldRefs;
}
export interface Prisma__TaskClient<T, Null = never, ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs, GlobalOmitOptions = {}> extends Prisma.PrismaPromise<T> {
    readonly [Symbol.toStringTag]: "PrismaPromise";
    project<T extends Prisma.Task$projectArgs<ExtArgs> = {}>(args?: Prisma.Subset<T, Prisma.Task$projectArgs<ExtArgs>>): Prisma.Prisma__ProjectClient<runtime.Types.Result.GetResult<Prisma.$ProjectPayload<ExtArgs>, T, "findUniqueOrThrow", GlobalOmitOptions> | null, null, ExtArgs, GlobalOmitOptions>;
    workspace<T extends Prisma.WorkspaceDefaultArgs<ExtArgs> = {}>(args?: Prisma.Subset<T, Prisma.WorkspaceDefaultArgs<ExtArgs>>): Prisma.Prisma__WorkspaceClient<runtime.Types.Result.GetResult<Prisma.$WorkspacePayload<ExtArgs>, T, "findUniqueOrThrow", GlobalOmitOptions> | Null, Null, ExtArgs, GlobalOmitOptions>;
    assignee<T extends Prisma.Task$assigneeArgs<ExtArgs> = {}>(args?: Prisma.Subset<T, Prisma.Task$assigneeArgs<ExtArgs>>): Prisma.Prisma__UserClient<runtime.Types.Result.GetResult<Prisma.$UserPayload<ExtArgs>, T, "findUniqueOrThrow", GlobalOmitOptions> | null, null, ExtArgs, GlobalOmitOptions>;
    then<TResult1 = T, TResult2 = never>(onfulfilled?: ((value: T) => TResult1 | PromiseLike<TResult1>) | undefined | null, onrejected?: ((reason: any) => TResult2 | PromiseLike<TResult2>) | undefined | null): runtime.Types.Utils.JsPromise<TResult1 | TResult2>;
    catch<TResult = never>(onrejected?: ((reason: any) => TResult | PromiseLike<TResult>) | undefined | null): runtime.Types.Utils.JsPromise<T | TResult>;
    finally(onfinally?: (() => void) | undefined | null): runtime.Types.Utils.JsPromise<T>;
}
export interface TaskFieldRefs {
    readonly id: Prisma.FieldRef<"Task", 'String'>;
    readonly title: Prisma.FieldRef<"Task", 'String'>;
    readonly description: Prisma.FieldRef<"Task", 'String'>;
    readonly status: Prisma.FieldRef<"Task", 'TaskStatus'>;
    readonly dueDate: Prisma.FieldRef<"Task", 'DateTime'>;
    readonly projectId: Prisma.FieldRef<"Task", 'String'>;
    readonly workspaceId: Prisma.FieldRef<"Task", 'String'>;
    readonly assigneeId: Prisma.FieldRef<"Task", 'String'>;
    readonly createdAt: Prisma.FieldRef<"Task", 'DateTime'>;
    readonly updatedAt: Prisma.FieldRef<"Task", 'DateTime'>;
}
export type TaskFindUniqueArgs<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    select?: Prisma.TaskSelect<ExtArgs> | null;
    omit?: Prisma.TaskOmit<ExtArgs> | null;
    include?: Prisma.TaskInclude<ExtArgs> | null;
    where: Prisma.TaskWhereUniqueInput;
};
export type TaskFindUniqueOrThrowArgs<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    select?: Prisma.TaskSelect<ExtArgs> | null;
    omit?: Prisma.TaskOmit<ExtArgs> | null;
    include?: Prisma.TaskInclude<ExtArgs> | null;
    where: Prisma.TaskWhereUniqueInput;
};
export type TaskFindFirstArgs<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    select?: Prisma.TaskSelect<ExtArgs> | null;
    omit?: Prisma.TaskOmit<ExtArgs> | null;
    include?: Prisma.TaskInclude<ExtArgs> | null;
    where?: Prisma.TaskWhereInput;
    orderBy?: Prisma.TaskOrderByWithRelationInput | Prisma.TaskOrderByWithRelationInput[];
    cursor?: Prisma.TaskWhereUniqueInput;
    take?: number;
    skip?: number;
    distinct?: Prisma.TaskScalarFieldEnum | Prisma.TaskScalarFieldEnum[];
};
export type TaskFindFirstOrThrowArgs<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    select?: Prisma.TaskSelect<ExtArgs> | null;
    omit?: Prisma.TaskOmit<ExtArgs> | null;
    include?: Prisma.TaskInclude<ExtArgs> | null;
    where?: Prisma.TaskWhereInput;
    orderBy?: Prisma.TaskOrderByWithRelationInput | Prisma.TaskOrderByWithRelationInput[];
    cursor?: Prisma.TaskWhereUniqueInput;
    take?: number;
    skip?: number;
    distinct?: Prisma.TaskScalarFieldEnum | Prisma.TaskScalarFieldEnum[];
};
export type TaskFindManyArgs<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    select?: Prisma.TaskSelect<ExtArgs> | null;
    omit?: Prisma.TaskOmit<ExtArgs> | null;
    include?: Prisma.TaskInclude<ExtArgs> | null;
    where?: Prisma.TaskWhereInput;
    orderBy?: Prisma.TaskOrderByWithRelationInput | Prisma.TaskOrderByWithRelationInput[];
    cursor?: Prisma.TaskWhereUniqueInput;
    take?: number;
    skip?: number;
    distinct?: Prisma.TaskScalarFieldEnum | Prisma.TaskScalarFieldEnum[];
};
export type TaskCreateArgs<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    select?: Prisma.TaskSelect<ExtArgs> | null;
    omit?: Prisma.TaskOmit<ExtArgs> | null;
    include?: Prisma.TaskInclude<ExtArgs> | null;
    data: Prisma.XOR<Prisma.TaskCreateInput, Prisma.TaskUncheckedCreateInput>;
};
export type TaskCreateManyArgs<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    data: Prisma.TaskCreateManyInput | Prisma.TaskCreateManyInput[];
    skipDuplicates?: boolean;
};
export type TaskCreateManyAndReturnArgs<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    select?: Prisma.TaskSelectCreateManyAndReturn<ExtArgs> | null;
    omit?: Prisma.TaskOmit<ExtArgs> | null;
    data: Prisma.TaskCreateManyInput | Prisma.TaskCreateManyInput[];
    skipDuplicates?: boolean;
    include?: Prisma.TaskIncludeCreateManyAndReturn<ExtArgs> | null;
};
export type TaskUpdateArgs<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    select?: Prisma.TaskSelect<ExtArgs> | null;
    omit?: Prisma.TaskOmit<ExtArgs> | null;
    include?: Prisma.TaskInclude<ExtArgs> | null;
    data: Prisma.XOR<Prisma.TaskUpdateInput, Prisma.TaskUncheckedUpdateInput>;
    where: Prisma.TaskWhereUniqueInput;
};
export type TaskUpdateManyArgs<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    data: Prisma.XOR<Prisma.TaskUpdateManyMutationInput, Prisma.TaskUncheckedUpdateManyInput>;
    where?: Prisma.TaskWhereInput;
    limit?: number;
};
export type TaskUpdateManyAndReturnArgs<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    select?: Prisma.TaskSelectUpdateManyAndReturn<ExtArgs> | null;
    omit?: Prisma.TaskOmit<ExtArgs> | null;
    data: Prisma.XOR<Prisma.TaskUpdateManyMutationInput, Prisma.TaskUncheckedUpdateManyInput>;
    where?: Prisma.TaskWhereInput;
    limit?: number;
    include?: Prisma.TaskIncludeUpdateManyAndReturn<ExtArgs> | null;
};
export type TaskUpsertArgs<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    select?: Prisma.TaskSelect<ExtArgs> | null;
    omit?: Prisma.TaskOmit<ExtArgs> | null;
    include?: Prisma.TaskInclude<ExtArgs> | null;
    where: Prisma.TaskWhereUniqueInput;
    create: Prisma.XOR<Prisma.TaskCreateInput, Prisma.TaskUncheckedCreateInput>;
    update: Prisma.XOR<Prisma.TaskUpdateInput, Prisma.TaskUncheckedUpdateInput>;
};
export type TaskDeleteArgs<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    select?: Prisma.TaskSelect<ExtArgs> | null;
    omit?: Prisma.TaskOmit<ExtArgs> | null;
    include?: Prisma.TaskInclude<ExtArgs> | null;
    where: Prisma.TaskWhereUniqueInput;
};
export type TaskDeleteManyArgs<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    where?: Prisma.TaskWhereInput;
    limit?: number;
};
export type Task$projectArgs<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    select?: Prisma.ProjectSelect<ExtArgs> | null;
    omit?: Prisma.ProjectOmit<ExtArgs> | null;
    include?: Prisma.ProjectInclude<ExtArgs> | null;
    where?: Prisma.ProjectWhereInput;
};
export type Task$assigneeArgs<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    select?: Prisma.UserSelect<ExtArgs> | null;
    omit?: Prisma.UserOmit<ExtArgs> | null;
    include?: Prisma.UserInclude<ExtArgs> | null;
    where?: Prisma.UserWhereInput;
};
export type TaskDefaultArgs<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    select?: Prisma.TaskSelect<ExtArgs> | null;
    omit?: Prisma.TaskOmit<ExtArgs> | null;
    include?: Prisma.TaskInclude<ExtArgs> | null;
};
