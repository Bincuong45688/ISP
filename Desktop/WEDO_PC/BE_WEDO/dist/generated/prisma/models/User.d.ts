import type * as runtime from "@prisma/client/runtime/client";
import type * as Prisma from "../internal/prismaNamespace.js";
export type UserModel = runtime.Types.Result.DefaultSelection<Prisma.$UserPayload>;
export type AggregateUser = {
    _count: UserCountAggregateOutputType | null;
    _min: UserMinAggregateOutputType | null;
    _max: UserMaxAggregateOutputType | null;
};
export type UserMinAggregateOutputType = {
    id: string | null;
    email: string | null;
    passwordHash: string | null;
    fullName: string | null;
    avatarUrl: string | null;
    createdAt: Date | null;
    updatedAt: Date | null;
};
export type UserMaxAggregateOutputType = {
    id: string | null;
    email: string | null;
    passwordHash: string | null;
    fullName: string | null;
    avatarUrl: string | null;
    createdAt: Date | null;
    updatedAt: Date | null;
};
export type UserCountAggregateOutputType = {
    id: number;
    email: number;
    passwordHash: number;
    fullName: number;
    avatarUrl: number;
    createdAt: number;
    updatedAt: number;
    _all: number;
};
export type UserMinAggregateInputType = {
    id?: true;
    email?: true;
    passwordHash?: true;
    fullName?: true;
    avatarUrl?: true;
    createdAt?: true;
    updatedAt?: true;
};
export type UserMaxAggregateInputType = {
    id?: true;
    email?: true;
    passwordHash?: true;
    fullName?: true;
    avatarUrl?: true;
    createdAt?: true;
    updatedAt?: true;
};
export type UserCountAggregateInputType = {
    id?: true;
    email?: true;
    passwordHash?: true;
    fullName?: true;
    avatarUrl?: true;
    createdAt?: true;
    updatedAt?: true;
    _all?: true;
};
export type UserAggregateArgs<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    where?: Prisma.UserWhereInput;
    orderBy?: Prisma.UserOrderByWithRelationInput | Prisma.UserOrderByWithRelationInput[];
    cursor?: Prisma.UserWhereUniqueInput;
    take?: number;
    skip?: number;
    _count?: true | UserCountAggregateInputType;
    _min?: UserMinAggregateInputType;
    _max?: UserMaxAggregateInputType;
};
export type GetUserAggregateType<T extends UserAggregateArgs> = {
    [P in keyof T & keyof AggregateUser]: P extends '_count' | 'count' ? T[P] extends true ? number : Prisma.GetScalarType<T[P], AggregateUser[P]> : Prisma.GetScalarType<T[P], AggregateUser[P]>;
};
export type UserGroupByArgs<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    where?: Prisma.UserWhereInput;
    orderBy?: Prisma.UserOrderByWithAggregationInput | Prisma.UserOrderByWithAggregationInput[];
    by: Prisma.UserScalarFieldEnum[] | Prisma.UserScalarFieldEnum;
    having?: Prisma.UserScalarWhereWithAggregatesInput;
    take?: number;
    skip?: number;
    _count?: UserCountAggregateInputType | true;
    _min?: UserMinAggregateInputType;
    _max?: UserMaxAggregateInputType;
};
export type UserGroupByOutputType = {
    id: string;
    email: string;
    passwordHash: string;
    fullName: string;
    avatarUrl: string | null;
    createdAt: Date;
    updatedAt: Date;
    _count: UserCountAggregateOutputType | null;
    _min: UserMinAggregateOutputType | null;
    _max: UserMaxAggregateOutputType | null;
};
export type GetUserGroupByPayload<T extends UserGroupByArgs> = Prisma.PrismaPromise<Array<Prisma.PickEnumerable<UserGroupByOutputType, T['by']> & {
    [P in ((keyof T) & (keyof UserGroupByOutputType))]: P extends '_count' ? T[P] extends boolean ? number : Prisma.GetScalarType<T[P], UserGroupByOutputType[P]> : Prisma.GetScalarType<T[P], UserGroupByOutputType[P]>;
}>>;
export type UserWhereInput = {
    AND?: Prisma.UserWhereInput | Prisma.UserWhereInput[];
    OR?: Prisma.UserWhereInput[];
    NOT?: Prisma.UserWhereInput | Prisma.UserWhereInput[];
    id?: Prisma.StringFilter<"User"> | string;
    email?: Prisma.StringFilter<"User"> | string;
    passwordHash?: Prisma.StringFilter<"User"> | string;
    fullName?: Prisma.StringFilter<"User"> | string;
    avatarUrl?: Prisma.StringNullableFilter<"User"> | string | null;
    createdAt?: Prisma.DateTimeFilter<"User"> | Date | string;
    updatedAt?: Prisma.DateTimeFilter<"User"> | Date | string;
    workspaces?: Prisma.WorkspaceMemberListRelationFilter;
    ownedWorkspaces?: Prisma.WorkspaceListRelationFilter;
    tasks?: Prisma.TaskListRelationFilter;
    events?: Prisma.EventListRelationFilter;
};
export type UserOrderByWithRelationInput = {
    id?: Prisma.SortOrder;
    email?: Prisma.SortOrder;
    passwordHash?: Prisma.SortOrder;
    fullName?: Prisma.SortOrder;
    avatarUrl?: Prisma.SortOrderInput | Prisma.SortOrder;
    createdAt?: Prisma.SortOrder;
    updatedAt?: Prisma.SortOrder;
    workspaces?: Prisma.WorkspaceMemberOrderByRelationAggregateInput;
    ownedWorkspaces?: Prisma.WorkspaceOrderByRelationAggregateInput;
    tasks?: Prisma.TaskOrderByRelationAggregateInput;
    events?: Prisma.EventOrderByRelationAggregateInput;
};
export type UserWhereUniqueInput = Prisma.AtLeast<{
    id?: string;
    email?: string;
    AND?: Prisma.UserWhereInput | Prisma.UserWhereInput[];
    OR?: Prisma.UserWhereInput[];
    NOT?: Prisma.UserWhereInput | Prisma.UserWhereInput[];
    passwordHash?: Prisma.StringFilter<"User"> | string;
    fullName?: Prisma.StringFilter<"User"> | string;
    avatarUrl?: Prisma.StringNullableFilter<"User"> | string | null;
    createdAt?: Prisma.DateTimeFilter<"User"> | Date | string;
    updatedAt?: Prisma.DateTimeFilter<"User"> | Date | string;
    workspaces?: Prisma.WorkspaceMemberListRelationFilter;
    ownedWorkspaces?: Prisma.WorkspaceListRelationFilter;
    tasks?: Prisma.TaskListRelationFilter;
    events?: Prisma.EventListRelationFilter;
}, "id" | "email">;
export type UserOrderByWithAggregationInput = {
    id?: Prisma.SortOrder;
    email?: Prisma.SortOrder;
    passwordHash?: Prisma.SortOrder;
    fullName?: Prisma.SortOrder;
    avatarUrl?: Prisma.SortOrderInput | Prisma.SortOrder;
    createdAt?: Prisma.SortOrder;
    updatedAt?: Prisma.SortOrder;
    _count?: Prisma.UserCountOrderByAggregateInput;
    _max?: Prisma.UserMaxOrderByAggregateInput;
    _min?: Prisma.UserMinOrderByAggregateInput;
};
export type UserScalarWhereWithAggregatesInput = {
    AND?: Prisma.UserScalarWhereWithAggregatesInput | Prisma.UserScalarWhereWithAggregatesInput[];
    OR?: Prisma.UserScalarWhereWithAggregatesInput[];
    NOT?: Prisma.UserScalarWhereWithAggregatesInput | Prisma.UserScalarWhereWithAggregatesInput[];
    id?: Prisma.StringWithAggregatesFilter<"User"> | string;
    email?: Prisma.StringWithAggregatesFilter<"User"> | string;
    passwordHash?: Prisma.StringWithAggregatesFilter<"User"> | string;
    fullName?: Prisma.StringWithAggregatesFilter<"User"> | string;
    avatarUrl?: Prisma.StringNullableWithAggregatesFilter<"User"> | string | null;
    createdAt?: Prisma.DateTimeWithAggregatesFilter<"User"> | Date | string;
    updatedAt?: Prisma.DateTimeWithAggregatesFilter<"User"> | Date | string;
};
export type UserCreateInput = {
    id?: string;
    email: string;
    passwordHash: string;
    fullName: string;
    avatarUrl?: string | null;
    createdAt?: Date | string;
    updatedAt?: Date | string;
    workspaces?: Prisma.WorkspaceMemberCreateNestedManyWithoutUserInput;
    ownedWorkspaces?: Prisma.WorkspaceCreateNestedManyWithoutOwnerInput;
    tasks?: Prisma.TaskCreateNestedManyWithoutAssigneeInput;
    events?: Prisma.EventCreateNestedManyWithoutCreatorInput;
};
export type UserUncheckedCreateInput = {
    id?: string;
    email: string;
    passwordHash: string;
    fullName: string;
    avatarUrl?: string | null;
    createdAt?: Date | string;
    updatedAt?: Date | string;
    workspaces?: Prisma.WorkspaceMemberUncheckedCreateNestedManyWithoutUserInput;
    ownedWorkspaces?: Prisma.WorkspaceUncheckedCreateNestedManyWithoutOwnerInput;
    tasks?: Prisma.TaskUncheckedCreateNestedManyWithoutAssigneeInput;
    events?: Prisma.EventUncheckedCreateNestedManyWithoutCreatorInput;
};
export type UserUpdateInput = {
    id?: Prisma.StringFieldUpdateOperationsInput | string;
    email?: Prisma.StringFieldUpdateOperationsInput | string;
    passwordHash?: Prisma.StringFieldUpdateOperationsInput | string;
    fullName?: Prisma.StringFieldUpdateOperationsInput | string;
    avatarUrl?: Prisma.NullableStringFieldUpdateOperationsInput | string | null;
    createdAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    updatedAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    workspaces?: Prisma.WorkspaceMemberUpdateManyWithoutUserNestedInput;
    ownedWorkspaces?: Prisma.WorkspaceUpdateManyWithoutOwnerNestedInput;
    tasks?: Prisma.TaskUpdateManyWithoutAssigneeNestedInput;
    events?: Prisma.EventUpdateManyWithoutCreatorNestedInput;
};
export type UserUncheckedUpdateInput = {
    id?: Prisma.StringFieldUpdateOperationsInput | string;
    email?: Prisma.StringFieldUpdateOperationsInput | string;
    passwordHash?: Prisma.StringFieldUpdateOperationsInput | string;
    fullName?: Prisma.StringFieldUpdateOperationsInput | string;
    avatarUrl?: Prisma.NullableStringFieldUpdateOperationsInput | string | null;
    createdAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    updatedAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    workspaces?: Prisma.WorkspaceMemberUncheckedUpdateManyWithoutUserNestedInput;
    ownedWorkspaces?: Prisma.WorkspaceUncheckedUpdateManyWithoutOwnerNestedInput;
    tasks?: Prisma.TaskUncheckedUpdateManyWithoutAssigneeNestedInput;
    events?: Prisma.EventUncheckedUpdateManyWithoutCreatorNestedInput;
};
export type UserCreateManyInput = {
    id?: string;
    email: string;
    passwordHash: string;
    fullName: string;
    avatarUrl?: string | null;
    createdAt?: Date | string;
    updatedAt?: Date | string;
};
export type UserUpdateManyMutationInput = {
    id?: Prisma.StringFieldUpdateOperationsInput | string;
    email?: Prisma.StringFieldUpdateOperationsInput | string;
    passwordHash?: Prisma.StringFieldUpdateOperationsInput | string;
    fullName?: Prisma.StringFieldUpdateOperationsInput | string;
    avatarUrl?: Prisma.NullableStringFieldUpdateOperationsInput | string | null;
    createdAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    updatedAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
};
export type UserUncheckedUpdateManyInput = {
    id?: Prisma.StringFieldUpdateOperationsInput | string;
    email?: Prisma.StringFieldUpdateOperationsInput | string;
    passwordHash?: Prisma.StringFieldUpdateOperationsInput | string;
    fullName?: Prisma.StringFieldUpdateOperationsInput | string;
    avatarUrl?: Prisma.NullableStringFieldUpdateOperationsInput | string | null;
    createdAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    updatedAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
};
export type UserCountOrderByAggregateInput = {
    id?: Prisma.SortOrder;
    email?: Prisma.SortOrder;
    passwordHash?: Prisma.SortOrder;
    fullName?: Prisma.SortOrder;
    avatarUrl?: Prisma.SortOrder;
    createdAt?: Prisma.SortOrder;
    updatedAt?: Prisma.SortOrder;
};
export type UserMaxOrderByAggregateInput = {
    id?: Prisma.SortOrder;
    email?: Prisma.SortOrder;
    passwordHash?: Prisma.SortOrder;
    fullName?: Prisma.SortOrder;
    avatarUrl?: Prisma.SortOrder;
    createdAt?: Prisma.SortOrder;
    updatedAt?: Prisma.SortOrder;
};
export type UserMinOrderByAggregateInput = {
    id?: Prisma.SortOrder;
    email?: Prisma.SortOrder;
    passwordHash?: Prisma.SortOrder;
    fullName?: Prisma.SortOrder;
    avatarUrl?: Prisma.SortOrder;
    createdAt?: Prisma.SortOrder;
    updatedAt?: Prisma.SortOrder;
};
export type UserScalarRelationFilter = {
    is?: Prisma.UserWhereInput;
    isNot?: Prisma.UserWhereInput;
};
export type UserNullableScalarRelationFilter = {
    is?: Prisma.UserWhereInput | null;
    isNot?: Prisma.UserWhereInput | null;
};
export type StringFieldUpdateOperationsInput = {
    set?: string;
};
export type NullableStringFieldUpdateOperationsInput = {
    set?: string | null;
};
export type DateTimeFieldUpdateOperationsInput = {
    set?: Date | string;
};
export type UserCreateNestedOneWithoutOwnedWorkspacesInput = {
    create?: Prisma.XOR<Prisma.UserCreateWithoutOwnedWorkspacesInput, Prisma.UserUncheckedCreateWithoutOwnedWorkspacesInput>;
    connectOrCreate?: Prisma.UserCreateOrConnectWithoutOwnedWorkspacesInput;
    connect?: Prisma.UserWhereUniqueInput;
};
export type UserUpdateOneRequiredWithoutOwnedWorkspacesNestedInput = {
    create?: Prisma.XOR<Prisma.UserCreateWithoutOwnedWorkspacesInput, Prisma.UserUncheckedCreateWithoutOwnedWorkspacesInput>;
    connectOrCreate?: Prisma.UserCreateOrConnectWithoutOwnedWorkspacesInput;
    upsert?: Prisma.UserUpsertWithoutOwnedWorkspacesInput;
    connect?: Prisma.UserWhereUniqueInput;
    update?: Prisma.XOR<Prisma.XOR<Prisma.UserUpdateToOneWithWhereWithoutOwnedWorkspacesInput, Prisma.UserUpdateWithoutOwnedWorkspacesInput>, Prisma.UserUncheckedUpdateWithoutOwnedWorkspacesInput>;
};
export type UserCreateNestedOneWithoutWorkspacesInput = {
    create?: Prisma.XOR<Prisma.UserCreateWithoutWorkspacesInput, Prisma.UserUncheckedCreateWithoutWorkspacesInput>;
    connectOrCreate?: Prisma.UserCreateOrConnectWithoutWorkspacesInput;
    connect?: Prisma.UserWhereUniqueInput;
};
export type UserUpdateOneRequiredWithoutWorkspacesNestedInput = {
    create?: Prisma.XOR<Prisma.UserCreateWithoutWorkspacesInput, Prisma.UserUncheckedCreateWithoutWorkspacesInput>;
    connectOrCreate?: Prisma.UserCreateOrConnectWithoutWorkspacesInput;
    upsert?: Prisma.UserUpsertWithoutWorkspacesInput;
    connect?: Prisma.UserWhereUniqueInput;
    update?: Prisma.XOR<Prisma.XOR<Prisma.UserUpdateToOneWithWhereWithoutWorkspacesInput, Prisma.UserUpdateWithoutWorkspacesInput>, Prisma.UserUncheckedUpdateWithoutWorkspacesInput>;
};
export type UserCreateNestedOneWithoutTasksInput = {
    create?: Prisma.XOR<Prisma.UserCreateWithoutTasksInput, Prisma.UserUncheckedCreateWithoutTasksInput>;
    connectOrCreate?: Prisma.UserCreateOrConnectWithoutTasksInput;
    connect?: Prisma.UserWhereUniqueInput;
};
export type UserUpdateOneWithoutTasksNestedInput = {
    create?: Prisma.XOR<Prisma.UserCreateWithoutTasksInput, Prisma.UserUncheckedCreateWithoutTasksInput>;
    connectOrCreate?: Prisma.UserCreateOrConnectWithoutTasksInput;
    upsert?: Prisma.UserUpsertWithoutTasksInput;
    disconnect?: Prisma.UserWhereInput | boolean;
    delete?: Prisma.UserWhereInput | boolean;
    connect?: Prisma.UserWhereUniqueInput;
    update?: Prisma.XOR<Prisma.XOR<Prisma.UserUpdateToOneWithWhereWithoutTasksInput, Prisma.UserUpdateWithoutTasksInput>, Prisma.UserUncheckedUpdateWithoutTasksInput>;
};
export type UserCreateNestedOneWithoutEventsInput = {
    create?: Prisma.XOR<Prisma.UserCreateWithoutEventsInput, Prisma.UserUncheckedCreateWithoutEventsInput>;
    connectOrCreate?: Prisma.UserCreateOrConnectWithoutEventsInput;
    connect?: Prisma.UserWhereUniqueInput;
};
export type UserUpdateOneRequiredWithoutEventsNestedInput = {
    create?: Prisma.XOR<Prisma.UserCreateWithoutEventsInput, Prisma.UserUncheckedCreateWithoutEventsInput>;
    connectOrCreate?: Prisma.UserCreateOrConnectWithoutEventsInput;
    upsert?: Prisma.UserUpsertWithoutEventsInput;
    connect?: Prisma.UserWhereUniqueInput;
    update?: Prisma.XOR<Prisma.XOR<Prisma.UserUpdateToOneWithWhereWithoutEventsInput, Prisma.UserUpdateWithoutEventsInput>, Prisma.UserUncheckedUpdateWithoutEventsInput>;
};
export type UserCreateWithoutOwnedWorkspacesInput = {
    id?: string;
    email: string;
    passwordHash: string;
    fullName: string;
    avatarUrl?: string | null;
    createdAt?: Date | string;
    updatedAt?: Date | string;
    workspaces?: Prisma.WorkspaceMemberCreateNestedManyWithoutUserInput;
    tasks?: Prisma.TaskCreateNestedManyWithoutAssigneeInput;
    events?: Prisma.EventCreateNestedManyWithoutCreatorInput;
};
export type UserUncheckedCreateWithoutOwnedWorkspacesInput = {
    id?: string;
    email: string;
    passwordHash: string;
    fullName: string;
    avatarUrl?: string | null;
    createdAt?: Date | string;
    updatedAt?: Date | string;
    workspaces?: Prisma.WorkspaceMemberUncheckedCreateNestedManyWithoutUserInput;
    tasks?: Prisma.TaskUncheckedCreateNestedManyWithoutAssigneeInput;
    events?: Prisma.EventUncheckedCreateNestedManyWithoutCreatorInput;
};
export type UserCreateOrConnectWithoutOwnedWorkspacesInput = {
    where: Prisma.UserWhereUniqueInput;
    create: Prisma.XOR<Prisma.UserCreateWithoutOwnedWorkspacesInput, Prisma.UserUncheckedCreateWithoutOwnedWorkspacesInput>;
};
export type UserUpsertWithoutOwnedWorkspacesInput = {
    update: Prisma.XOR<Prisma.UserUpdateWithoutOwnedWorkspacesInput, Prisma.UserUncheckedUpdateWithoutOwnedWorkspacesInput>;
    create: Prisma.XOR<Prisma.UserCreateWithoutOwnedWorkspacesInput, Prisma.UserUncheckedCreateWithoutOwnedWorkspacesInput>;
    where?: Prisma.UserWhereInput;
};
export type UserUpdateToOneWithWhereWithoutOwnedWorkspacesInput = {
    where?: Prisma.UserWhereInput;
    data: Prisma.XOR<Prisma.UserUpdateWithoutOwnedWorkspacesInput, Prisma.UserUncheckedUpdateWithoutOwnedWorkspacesInput>;
};
export type UserUpdateWithoutOwnedWorkspacesInput = {
    id?: Prisma.StringFieldUpdateOperationsInput | string;
    email?: Prisma.StringFieldUpdateOperationsInput | string;
    passwordHash?: Prisma.StringFieldUpdateOperationsInput | string;
    fullName?: Prisma.StringFieldUpdateOperationsInput | string;
    avatarUrl?: Prisma.NullableStringFieldUpdateOperationsInput | string | null;
    createdAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    updatedAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    workspaces?: Prisma.WorkspaceMemberUpdateManyWithoutUserNestedInput;
    tasks?: Prisma.TaskUpdateManyWithoutAssigneeNestedInput;
    events?: Prisma.EventUpdateManyWithoutCreatorNestedInput;
};
export type UserUncheckedUpdateWithoutOwnedWorkspacesInput = {
    id?: Prisma.StringFieldUpdateOperationsInput | string;
    email?: Prisma.StringFieldUpdateOperationsInput | string;
    passwordHash?: Prisma.StringFieldUpdateOperationsInput | string;
    fullName?: Prisma.StringFieldUpdateOperationsInput | string;
    avatarUrl?: Prisma.NullableStringFieldUpdateOperationsInput | string | null;
    createdAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    updatedAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    workspaces?: Prisma.WorkspaceMemberUncheckedUpdateManyWithoutUserNestedInput;
    tasks?: Prisma.TaskUncheckedUpdateManyWithoutAssigneeNestedInput;
    events?: Prisma.EventUncheckedUpdateManyWithoutCreatorNestedInput;
};
export type UserCreateWithoutWorkspacesInput = {
    id?: string;
    email: string;
    passwordHash: string;
    fullName: string;
    avatarUrl?: string | null;
    createdAt?: Date | string;
    updatedAt?: Date | string;
    ownedWorkspaces?: Prisma.WorkspaceCreateNestedManyWithoutOwnerInput;
    tasks?: Prisma.TaskCreateNestedManyWithoutAssigneeInput;
    events?: Prisma.EventCreateNestedManyWithoutCreatorInput;
};
export type UserUncheckedCreateWithoutWorkspacesInput = {
    id?: string;
    email: string;
    passwordHash: string;
    fullName: string;
    avatarUrl?: string | null;
    createdAt?: Date | string;
    updatedAt?: Date | string;
    ownedWorkspaces?: Prisma.WorkspaceUncheckedCreateNestedManyWithoutOwnerInput;
    tasks?: Prisma.TaskUncheckedCreateNestedManyWithoutAssigneeInput;
    events?: Prisma.EventUncheckedCreateNestedManyWithoutCreatorInput;
};
export type UserCreateOrConnectWithoutWorkspacesInput = {
    where: Prisma.UserWhereUniqueInput;
    create: Prisma.XOR<Prisma.UserCreateWithoutWorkspacesInput, Prisma.UserUncheckedCreateWithoutWorkspacesInput>;
};
export type UserUpsertWithoutWorkspacesInput = {
    update: Prisma.XOR<Prisma.UserUpdateWithoutWorkspacesInput, Prisma.UserUncheckedUpdateWithoutWorkspacesInput>;
    create: Prisma.XOR<Prisma.UserCreateWithoutWorkspacesInput, Prisma.UserUncheckedCreateWithoutWorkspacesInput>;
    where?: Prisma.UserWhereInput;
};
export type UserUpdateToOneWithWhereWithoutWorkspacesInput = {
    where?: Prisma.UserWhereInput;
    data: Prisma.XOR<Prisma.UserUpdateWithoutWorkspacesInput, Prisma.UserUncheckedUpdateWithoutWorkspacesInput>;
};
export type UserUpdateWithoutWorkspacesInput = {
    id?: Prisma.StringFieldUpdateOperationsInput | string;
    email?: Prisma.StringFieldUpdateOperationsInput | string;
    passwordHash?: Prisma.StringFieldUpdateOperationsInput | string;
    fullName?: Prisma.StringFieldUpdateOperationsInput | string;
    avatarUrl?: Prisma.NullableStringFieldUpdateOperationsInput | string | null;
    createdAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    updatedAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    ownedWorkspaces?: Prisma.WorkspaceUpdateManyWithoutOwnerNestedInput;
    tasks?: Prisma.TaskUpdateManyWithoutAssigneeNestedInput;
    events?: Prisma.EventUpdateManyWithoutCreatorNestedInput;
};
export type UserUncheckedUpdateWithoutWorkspacesInput = {
    id?: Prisma.StringFieldUpdateOperationsInput | string;
    email?: Prisma.StringFieldUpdateOperationsInput | string;
    passwordHash?: Prisma.StringFieldUpdateOperationsInput | string;
    fullName?: Prisma.StringFieldUpdateOperationsInput | string;
    avatarUrl?: Prisma.NullableStringFieldUpdateOperationsInput | string | null;
    createdAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    updatedAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    ownedWorkspaces?: Prisma.WorkspaceUncheckedUpdateManyWithoutOwnerNestedInput;
    tasks?: Prisma.TaskUncheckedUpdateManyWithoutAssigneeNestedInput;
    events?: Prisma.EventUncheckedUpdateManyWithoutCreatorNestedInput;
};
export type UserCreateWithoutTasksInput = {
    id?: string;
    email: string;
    passwordHash: string;
    fullName: string;
    avatarUrl?: string | null;
    createdAt?: Date | string;
    updatedAt?: Date | string;
    workspaces?: Prisma.WorkspaceMemberCreateNestedManyWithoutUserInput;
    ownedWorkspaces?: Prisma.WorkspaceCreateNestedManyWithoutOwnerInput;
    events?: Prisma.EventCreateNestedManyWithoutCreatorInput;
};
export type UserUncheckedCreateWithoutTasksInput = {
    id?: string;
    email: string;
    passwordHash: string;
    fullName: string;
    avatarUrl?: string | null;
    createdAt?: Date | string;
    updatedAt?: Date | string;
    workspaces?: Prisma.WorkspaceMemberUncheckedCreateNestedManyWithoutUserInput;
    ownedWorkspaces?: Prisma.WorkspaceUncheckedCreateNestedManyWithoutOwnerInput;
    events?: Prisma.EventUncheckedCreateNestedManyWithoutCreatorInput;
};
export type UserCreateOrConnectWithoutTasksInput = {
    where: Prisma.UserWhereUniqueInput;
    create: Prisma.XOR<Prisma.UserCreateWithoutTasksInput, Prisma.UserUncheckedCreateWithoutTasksInput>;
};
export type UserUpsertWithoutTasksInput = {
    update: Prisma.XOR<Prisma.UserUpdateWithoutTasksInput, Prisma.UserUncheckedUpdateWithoutTasksInput>;
    create: Prisma.XOR<Prisma.UserCreateWithoutTasksInput, Prisma.UserUncheckedCreateWithoutTasksInput>;
    where?: Prisma.UserWhereInput;
};
export type UserUpdateToOneWithWhereWithoutTasksInput = {
    where?: Prisma.UserWhereInput;
    data: Prisma.XOR<Prisma.UserUpdateWithoutTasksInput, Prisma.UserUncheckedUpdateWithoutTasksInput>;
};
export type UserUpdateWithoutTasksInput = {
    id?: Prisma.StringFieldUpdateOperationsInput | string;
    email?: Prisma.StringFieldUpdateOperationsInput | string;
    passwordHash?: Prisma.StringFieldUpdateOperationsInput | string;
    fullName?: Prisma.StringFieldUpdateOperationsInput | string;
    avatarUrl?: Prisma.NullableStringFieldUpdateOperationsInput | string | null;
    createdAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    updatedAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    workspaces?: Prisma.WorkspaceMemberUpdateManyWithoutUserNestedInput;
    ownedWorkspaces?: Prisma.WorkspaceUpdateManyWithoutOwnerNestedInput;
    events?: Prisma.EventUpdateManyWithoutCreatorNestedInput;
};
export type UserUncheckedUpdateWithoutTasksInput = {
    id?: Prisma.StringFieldUpdateOperationsInput | string;
    email?: Prisma.StringFieldUpdateOperationsInput | string;
    passwordHash?: Prisma.StringFieldUpdateOperationsInput | string;
    fullName?: Prisma.StringFieldUpdateOperationsInput | string;
    avatarUrl?: Prisma.NullableStringFieldUpdateOperationsInput | string | null;
    createdAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    updatedAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    workspaces?: Prisma.WorkspaceMemberUncheckedUpdateManyWithoutUserNestedInput;
    ownedWorkspaces?: Prisma.WorkspaceUncheckedUpdateManyWithoutOwnerNestedInput;
    events?: Prisma.EventUncheckedUpdateManyWithoutCreatorNestedInput;
};
export type UserCreateWithoutEventsInput = {
    id?: string;
    email: string;
    passwordHash: string;
    fullName: string;
    avatarUrl?: string | null;
    createdAt?: Date | string;
    updatedAt?: Date | string;
    workspaces?: Prisma.WorkspaceMemberCreateNestedManyWithoutUserInput;
    ownedWorkspaces?: Prisma.WorkspaceCreateNestedManyWithoutOwnerInput;
    tasks?: Prisma.TaskCreateNestedManyWithoutAssigneeInput;
};
export type UserUncheckedCreateWithoutEventsInput = {
    id?: string;
    email: string;
    passwordHash: string;
    fullName: string;
    avatarUrl?: string | null;
    createdAt?: Date | string;
    updatedAt?: Date | string;
    workspaces?: Prisma.WorkspaceMemberUncheckedCreateNestedManyWithoutUserInput;
    ownedWorkspaces?: Prisma.WorkspaceUncheckedCreateNestedManyWithoutOwnerInput;
    tasks?: Prisma.TaskUncheckedCreateNestedManyWithoutAssigneeInput;
};
export type UserCreateOrConnectWithoutEventsInput = {
    where: Prisma.UserWhereUniqueInput;
    create: Prisma.XOR<Prisma.UserCreateWithoutEventsInput, Prisma.UserUncheckedCreateWithoutEventsInput>;
};
export type UserUpsertWithoutEventsInput = {
    update: Prisma.XOR<Prisma.UserUpdateWithoutEventsInput, Prisma.UserUncheckedUpdateWithoutEventsInput>;
    create: Prisma.XOR<Prisma.UserCreateWithoutEventsInput, Prisma.UserUncheckedCreateWithoutEventsInput>;
    where?: Prisma.UserWhereInput;
};
export type UserUpdateToOneWithWhereWithoutEventsInput = {
    where?: Prisma.UserWhereInput;
    data: Prisma.XOR<Prisma.UserUpdateWithoutEventsInput, Prisma.UserUncheckedUpdateWithoutEventsInput>;
};
export type UserUpdateWithoutEventsInput = {
    id?: Prisma.StringFieldUpdateOperationsInput | string;
    email?: Prisma.StringFieldUpdateOperationsInput | string;
    passwordHash?: Prisma.StringFieldUpdateOperationsInput | string;
    fullName?: Prisma.StringFieldUpdateOperationsInput | string;
    avatarUrl?: Prisma.NullableStringFieldUpdateOperationsInput | string | null;
    createdAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    updatedAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    workspaces?: Prisma.WorkspaceMemberUpdateManyWithoutUserNestedInput;
    ownedWorkspaces?: Prisma.WorkspaceUpdateManyWithoutOwnerNestedInput;
    tasks?: Prisma.TaskUpdateManyWithoutAssigneeNestedInput;
};
export type UserUncheckedUpdateWithoutEventsInput = {
    id?: Prisma.StringFieldUpdateOperationsInput | string;
    email?: Prisma.StringFieldUpdateOperationsInput | string;
    passwordHash?: Prisma.StringFieldUpdateOperationsInput | string;
    fullName?: Prisma.StringFieldUpdateOperationsInput | string;
    avatarUrl?: Prisma.NullableStringFieldUpdateOperationsInput | string | null;
    createdAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    updatedAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    workspaces?: Prisma.WorkspaceMemberUncheckedUpdateManyWithoutUserNestedInput;
    ownedWorkspaces?: Prisma.WorkspaceUncheckedUpdateManyWithoutOwnerNestedInput;
    tasks?: Prisma.TaskUncheckedUpdateManyWithoutAssigneeNestedInput;
};
export type UserCountOutputType = {
    workspaces: number;
    ownedWorkspaces: number;
    tasks: number;
    events: number;
};
export type UserCountOutputTypeSelect<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    workspaces?: boolean | UserCountOutputTypeCountWorkspacesArgs;
    ownedWorkspaces?: boolean | UserCountOutputTypeCountOwnedWorkspacesArgs;
    tasks?: boolean | UserCountOutputTypeCountTasksArgs;
    events?: boolean | UserCountOutputTypeCountEventsArgs;
};
export type UserCountOutputTypeDefaultArgs<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    select?: Prisma.UserCountOutputTypeSelect<ExtArgs> | null;
};
export type UserCountOutputTypeCountWorkspacesArgs<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    where?: Prisma.WorkspaceMemberWhereInput;
};
export type UserCountOutputTypeCountOwnedWorkspacesArgs<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    where?: Prisma.WorkspaceWhereInput;
};
export type UserCountOutputTypeCountTasksArgs<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    where?: Prisma.TaskWhereInput;
};
export type UserCountOutputTypeCountEventsArgs<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    where?: Prisma.EventWhereInput;
};
export type UserSelect<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = runtime.Types.Extensions.GetSelect<{
    id?: boolean;
    email?: boolean;
    passwordHash?: boolean;
    fullName?: boolean;
    avatarUrl?: boolean;
    createdAt?: boolean;
    updatedAt?: boolean;
    workspaces?: boolean | Prisma.User$workspacesArgs<ExtArgs>;
    ownedWorkspaces?: boolean | Prisma.User$ownedWorkspacesArgs<ExtArgs>;
    tasks?: boolean | Prisma.User$tasksArgs<ExtArgs>;
    events?: boolean | Prisma.User$eventsArgs<ExtArgs>;
    _count?: boolean | Prisma.UserCountOutputTypeDefaultArgs<ExtArgs>;
}, ExtArgs["result"]["user"]>;
export type UserSelectCreateManyAndReturn<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = runtime.Types.Extensions.GetSelect<{
    id?: boolean;
    email?: boolean;
    passwordHash?: boolean;
    fullName?: boolean;
    avatarUrl?: boolean;
    createdAt?: boolean;
    updatedAt?: boolean;
}, ExtArgs["result"]["user"]>;
export type UserSelectUpdateManyAndReturn<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = runtime.Types.Extensions.GetSelect<{
    id?: boolean;
    email?: boolean;
    passwordHash?: boolean;
    fullName?: boolean;
    avatarUrl?: boolean;
    createdAt?: boolean;
    updatedAt?: boolean;
}, ExtArgs["result"]["user"]>;
export type UserSelectScalar = {
    id?: boolean;
    email?: boolean;
    passwordHash?: boolean;
    fullName?: boolean;
    avatarUrl?: boolean;
    createdAt?: boolean;
    updatedAt?: boolean;
};
export type UserOmit<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = runtime.Types.Extensions.GetOmit<"id" | "email" | "passwordHash" | "fullName" | "avatarUrl" | "createdAt" | "updatedAt", ExtArgs["result"]["user"]>;
export type UserInclude<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    workspaces?: boolean | Prisma.User$workspacesArgs<ExtArgs>;
    ownedWorkspaces?: boolean | Prisma.User$ownedWorkspacesArgs<ExtArgs>;
    tasks?: boolean | Prisma.User$tasksArgs<ExtArgs>;
    events?: boolean | Prisma.User$eventsArgs<ExtArgs>;
    _count?: boolean | Prisma.UserCountOutputTypeDefaultArgs<ExtArgs>;
};
export type UserIncludeCreateManyAndReturn<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {};
export type UserIncludeUpdateManyAndReturn<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {};
export type $UserPayload<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    name: "User";
    objects: {
        workspaces: Prisma.$WorkspaceMemberPayload<ExtArgs>[];
        ownedWorkspaces: Prisma.$WorkspacePayload<ExtArgs>[];
        tasks: Prisma.$TaskPayload<ExtArgs>[];
        events: Prisma.$EventPayload<ExtArgs>[];
    };
    scalars: runtime.Types.Extensions.GetPayloadResult<{
        id: string;
        email: string;
        passwordHash: string;
        fullName: string;
        avatarUrl: string | null;
        createdAt: Date;
        updatedAt: Date;
    }, ExtArgs["result"]["user"]>;
    composites: {};
};
export type UserGetPayload<S extends boolean | null | undefined | UserDefaultArgs> = runtime.Types.Result.GetResult<Prisma.$UserPayload, S>;
export type UserCountArgs<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = Omit<UserFindManyArgs, 'select' | 'include' | 'distinct' | 'omit'> & {
    select?: UserCountAggregateInputType | true;
};
export interface UserDelegate<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs, GlobalOmitOptions = {}> {
    [K: symbol]: {
        types: Prisma.TypeMap<ExtArgs>['model']['User'];
        meta: {
            name: 'User';
        };
    };
    findUnique<T extends UserFindUniqueArgs>(args: Prisma.SelectSubset<T, UserFindUniqueArgs<ExtArgs>>): Prisma.Prisma__UserClient<runtime.Types.Result.GetResult<Prisma.$UserPayload<ExtArgs>, T, "findUnique", GlobalOmitOptions> | null, null, ExtArgs, GlobalOmitOptions>;
    findUniqueOrThrow<T extends UserFindUniqueOrThrowArgs>(args: Prisma.SelectSubset<T, UserFindUniqueOrThrowArgs<ExtArgs>>): Prisma.Prisma__UserClient<runtime.Types.Result.GetResult<Prisma.$UserPayload<ExtArgs>, T, "findUniqueOrThrow", GlobalOmitOptions>, never, ExtArgs, GlobalOmitOptions>;
    findFirst<T extends UserFindFirstArgs>(args?: Prisma.SelectSubset<T, UserFindFirstArgs<ExtArgs>>): Prisma.Prisma__UserClient<runtime.Types.Result.GetResult<Prisma.$UserPayload<ExtArgs>, T, "findFirst", GlobalOmitOptions> | null, null, ExtArgs, GlobalOmitOptions>;
    findFirstOrThrow<T extends UserFindFirstOrThrowArgs>(args?: Prisma.SelectSubset<T, UserFindFirstOrThrowArgs<ExtArgs>>): Prisma.Prisma__UserClient<runtime.Types.Result.GetResult<Prisma.$UserPayload<ExtArgs>, T, "findFirstOrThrow", GlobalOmitOptions>, never, ExtArgs, GlobalOmitOptions>;
    findMany<T extends UserFindManyArgs>(args?: Prisma.SelectSubset<T, UserFindManyArgs<ExtArgs>>): Prisma.PrismaPromise<runtime.Types.Result.GetResult<Prisma.$UserPayload<ExtArgs>, T, "findMany", GlobalOmitOptions>>;
    create<T extends UserCreateArgs>(args: Prisma.SelectSubset<T, UserCreateArgs<ExtArgs>>): Prisma.Prisma__UserClient<runtime.Types.Result.GetResult<Prisma.$UserPayload<ExtArgs>, T, "create", GlobalOmitOptions>, never, ExtArgs, GlobalOmitOptions>;
    createMany<T extends UserCreateManyArgs>(args?: Prisma.SelectSubset<T, UserCreateManyArgs<ExtArgs>>): Prisma.PrismaPromise<Prisma.BatchPayload>;
    createManyAndReturn<T extends UserCreateManyAndReturnArgs>(args?: Prisma.SelectSubset<T, UserCreateManyAndReturnArgs<ExtArgs>>): Prisma.PrismaPromise<runtime.Types.Result.GetResult<Prisma.$UserPayload<ExtArgs>, T, "createManyAndReturn", GlobalOmitOptions>>;
    delete<T extends UserDeleteArgs>(args: Prisma.SelectSubset<T, UserDeleteArgs<ExtArgs>>): Prisma.Prisma__UserClient<runtime.Types.Result.GetResult<Prisma.$UserPayload<ExtArgs>, T, "delete", GlobalOmitOptions>, never, ExtArgs, GlobalOmitOptions>;
    update<T extends UserUpdateArgs>(args: Prisma.SelectSubset<T, UserUpdateArgs<ExtArgs>>): Prisma.Prisma__UserClient<runtime.Types.Result.GetResult<Prisma.$UserPayload<ExtArgs>, T, "update", GlobalOmitOptions>, never, ExtArgs, GlobalOmitOptions>;
    deleteMany<T extends UserDeleteManyArgs>(args?: Prisma.SelectSubset<T, UserDeleteManyArgs<ExtArgs>>): Prisma.PrismaPromise<Prisma.BatchPayload>;
    updateMany<T extends UserUpdateManyArgs>(args: Prisma.SelectSubset<T, UserUpdateManyArgs<ExtArgs>>): Prisma.PrismaPromise<Prisma.BatchPayload>;
    updateManyAndReturn<T extends UserUpdateManyAndReturnArgs>(args: Prisma.SelectSubset<T, UserUpdateManyAndReturnArgs<ExtArgs>>): Prisma.PrismaPromise<runtime.Types.Result.GetResult<Prisma.$UserPayload<ExtArgs>, T, "updateManyAndReturn", GlobalOmitOptions>>;
    upsert<T extends UserUpsertArgs>(args: Prisma.SelectSubset<T, UserUpsertArgs<ExtArgs>>): Prisma.Prisma__UserClient<runtime.Types.Result.GetResult<Prisma.$UserPayload<ExtArgs>, T, "upsert", GlobalOmitOptions>, never, ExtArgs, GlobalOmitOptions>;
    count<T extends UserCountArgs>(args?: Prisma.Subset<T, UserCountArgs>): Prisma.PrismaPromise<T extends runtime.Types.Utils.Record<'select', any> ? T['select'] extends true ? number : Prisma.GetScalarType<T['select'], UserCountAggregateOutputType> : number>;
    aggregate<T extends UserAggregateArgs>(args: Prisma.Subset<T, UserAggregateArgs>): Prisma.PrismaPromise<GetUserAggregateType<T>>;
    groupBy<T extends UserGroupByArgs, HasSelectOrTake extends Prisma.Or<Prisma.Extends<'skip', Prisma.Keys<T>>, Prisma.Extends<'take', Prisma.Keys<T>>>, OrderByArg extends Prisma.True extends HasSelectOrTake ? {
        orderBy: UserGroupByArgs['orderBy'];
    } : {
        orderBy?: UserGroupByArgs['orderBy'];
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
    }[OrderFields]>(args: Prisma.SubsetIntersection<T, UserGroupByArgs, OrderByArg> & InputErrors): {} extends InputErrors ? GetUserGroupByPayload<T> : Prisma.PrismaPromise<InputErrors>;
    readonly fields: UserFieldRefs;
}
export interface Prisma__UserClient<T, Null = never, ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs, GlobalOmitOptions = {}> extends Prisma.PrismaPromise<T> {
    readonly [Symbol.toStringTag]: "PrismaPromise";
    workspaces<T extends Prisma.User$workspacesArgs<ExtArgs> = {}>(args?: Prisma.Subset<T, Prisma.User$workspacesArgs<ExtArgs>>): Prisma.PrismaPromise<runtime.Types.Result.GetResult<Prisma.$WorkspaceMemberPayload<ExtArgs>, T, "findMany", GlobalOmitOptions> | Null>;
    ownedWorkspaces<T extends Prisma.User$ownedWorkspacesArgs<ExtArgs> = {}>(args?: Prisma.Subset<T, Prisma.User$ownedWorkspacesArgs<ExtArgs>>): Prisma.PrismaPromise<runtime.Types.Result.GetResult<Prisma.$WorkspacePayload<ExtArgs>, T, "findMany", GlobalOmitOptions> | Null>;
    tasks<T extends Prisma.User$tasksArgs<ExtArgs> = {}>(args?: Prisma.Subset<T, Prisma.User$tasksArgs<ExtArgs>>): Prisma.PrismaPromise<runtime.Types.Result.GetResult<Prisma.$TaskPayload<ExtArgs>, T, "findMany", GlobalOmitOptions> | Null>;
    events<T extends Prisma.User$eventsArgs<ExtArgs> = {}>(args?: Prisma.Subset<T, Prisma.User$eventsArgs<ExtArgs>>): Prisma.PrismaPromise<runtime.Types.Result.GetResult<Prisma.$EventPayload<ExtArgs>, T, "findMany", GlobalOmitOptions> | Null>;
    then<TResult1 = T, TResult2 = never>(onfulfilled?: ((value: T) => TResult1 | PromiseLike<TResult1>) | undefined | null, onrejected?: ((reason: any) => TResult2 | PromiseLike<TResult2>) | undefined | null): runtime.Types.Utils.JsPromise<TResult1 | TResult2>;
    catch<TResult = never>(onrejected?: ((reason: any) => TResult | PromiseLike<TResult>) | undefined | null): runtime.Types.Utils.JsPromise<T | TResult>;
    finally(onfinally?: (() => void) | undefined | null): runtime.Types.Utils.JsPromise<T>;
}
export interface UserFieldRefs {
    readonly id: Prisma.FieldRef<"User", 'String'>;
    readonly email: Prisma.FieldRef<"User", 'String'>;
    readonly passwordHash: Prisma.FieldRef<"User", 'String'>;
    readonly fullName: Prisma.FieldRef<"User", 'String'>;
    readonly avatarUrl: Prisma.FieldRef<"User", 'String'>;
    readonly createdAt: Prisma.FieldRef<"User", 'DateTime'>;
    readonly updatedAt: Prisma.FieldRef<"User", 'DateTime'>;
}
export type UserFindUniqueArgs<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    select?: Prisma.UserSelect<ExtArgs> | null;
    omit?: Prisma.UserOmit<ExtArgs> | null;
    include?: Prisma.UserInclude<ExtArgs> | null;
    where: Prisma.UserWhereUniqueInput;
};
export type UserFindUniqueOrThrowArgs<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    select?: Prisma.UserSelect<ExtArgs> | null;
    omit?: Prisma.UserOmit<ExtArgs> | null;
    include?: Prisma.UserInclude<ExtArgs> | null;
    where: Prisma.UserWhereUniqueInput;
};
export type UserFindFirstArgs<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    select?: Prisma.UserSelect<ExtArgs> | null;
    omit?: Prisma.UserOmit<ExtArgs> | null;
    include?: Prisma.UserInclude<ExtArgs> | null;
    where?: Prisma.UserWhereInput;
    orderBy?: Prisma.UserOrderByWithRelationInput | Prisma.UserOrderByWithRelationInput[];
    cursor?: Prisma.UserWhereUniqueInput;
    take?: number;
    skip?: number;
    distinct?: Prisma.UserScalarFieldEnum | Prisma.UserScalarFieldEnum[];
};
export type UserFindFirstOrThrowArgs<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    select?: Prisma.UserSelect<ExtArgs> | null;
    omit?: Prisma.UserOmit<ExtArgs> | null;
    include?: Prisma.UserInclude<ExtArgs> | null;
    where?: Prisma.UserWhereInput;
    orderBy?: Prisma.UserOrderByWithRelationInput | Prisma.UserOrderByWithRelationInput[];
    cursor?: Prisma.UserWhereUniqueInput;
    take?: number;
    skip?: number;
    distinct?: Prisma.UserScalarFieldEnum | Prisma.UserScalarFieldEnum[];
};
export type UserFindManyArgs<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    select?: Prisma.UserSelect<ExtArgs> | null;
    omit?: Prisma.UserOmit<ExtArgs> | null;
    include?: Prisma.UserInclude<ExtArgs> | null;
    where?: Prisma.UserWhereInput;
    orderBy?: Prisma.UserOrderByWithRelationInput | Prisma.UserOrderByWithRelationInput[];
    cursor?: Prisma.UserWhereUniqueInput;
    take?: number;
    skip?: number;
    distinct?: Prisma.UserScalarFieldEnum | Prisma.UserScalarFieldEnum[];
};
export type UserCreateArgs<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    select?: Prisma.UserSelect<ExtArgs> | null;
    omit?: Prisma.UserOmit<ExtArgs> | null;
    include?: Prisma.UserInclude<ExtArgs> | null;
    data: Prisma.XOR<Prisma.UserCreateInput, Prisma.UserUncheckedCreateInput>;
};
export type UserCreateManyArgs<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    data: Prisma.UserCreateManyInput | Prisma.UserCreateManyInput[];
    skipDuplicates?: boolean;
};
export type UserCreateManyAndReturnArgs<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    select?: Prisma.UserSelectCreateManyAndReturn<ExtArgs> | null;
    omit?: Prisma.UserOmit<ExtArgs> | null;
    data: Prisma.UserCreateManyInput | Prisma.UserCreateManyInput[];
    skipDuplicates?: boolean;
};
export type UserUpdateArgs<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    select?: Prisma.UserSelect<ExtArgs> | null;
    omit?: Prisma.UserOmit<ExtArgs> | null;
    include?: Prisma.UserInclude<ExtArgs> | null;
    data: Prisma.XOR<Prisma.UserUpdateInput, Prisma.UserUncheckedUpdateInput>;
    where: Prisma.UserWhereUniqueInput;
};
export type UserUpdateManyArgs<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    data: Prisma.XOR<Prisma.UserUpdateManyMutationInput, Prisma.UserUncheckedUpdateManyInput>;
    where?: Prisma.UserWhereInput;
    limit?: number;
};
export type UserUpdateManyAndReturnArgs<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    select?: Prisma.UserSelectUpdateManyAndReturn<ExtArgs> | null;
    omit?: Prisma.UserOmit<ExtArgs> | null;
    data: Prisma.XOR<Prisma.UserUpdateManyMutationInput, Prisma.UserUncheckedUpdateManyInput>;
    where?: Prisma.UserWhereInput;
    limit?: number;
};
export type UserUpsertArgs<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    select?: Prisma.UserSelect<ExtArgs> | null;
    omit?: Prisma.UserOmit<ExtArgs> | null;
    include?: Prisma.UserInclude<ExtArgs> | null;
    where: Prisma.UserWhereUniqueInput;
    create: Prisma.XOR<Prisma.UserCreateInput, Prisma.UserUncheckedCreateInput>;
    update: Prisma.XOR<Prisma.UserUpdateInput, Prisma.UserUncheckedUpdateInput>;
};
export type UserDeleteArgs<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    select?: Prisma.UserSelect<ExtArgs> | null;
    omit?: Prisma.UserOmit<ExtArgs> | null;
    include?: Prisma.UserInclude<ExtArgs> | null;
    where: Prisma.UserWhereUniqueInput;
};
export type UserDeleteManyArgs<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    where?: Prisma.UserWhereInput;
    limit?: number;
};
export type User$workspacesArgs<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    select?: Prisma.WorkspaceMemberSelect<ExtArgs> | null;
    omit?: Prisma.WorkspaceMemberOmit<ExtArgs> | null;
    include?: Prisma.WorkspaceMemberInclude<ExtArgs> | null;
    where?: Prisma.WorkspaceMemberWhereInput;
    orderBy?: Prisma.WorkspaceMemberOrderByWithRelationInput | Prisma.WorkspaceMemberOrderByWithRelationInput[];
    cursor?: Prisma.WorkspaceMemberWhereUniqueInput;
    take?: number;
    skip?: number;
    distinct?: Prisma.WorkspaceMemberScalarFieldEnum | Prisma.WorkspaceMemberScalarFieldEnum[];
};
export type User$ownedWorkspacesArgs<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    select?: Prisma.WorkspaceSelect<ExtArgs> | null;
    omit?: Prisma.WorkspaceOmit<ExtArgs> | null;
    include?: Prisma.WorkspaceInclude<ExtArgs> | null;
    where?: Prisma.WorkspaceWhereInput;
    orderBy?: Prisma.WorkspaceOrderByWithRelationInput | Prisma.WorkspaceOrderByWithRelationInput[];
    cursor?: Prisma.WorkspaceWhereUniqueInput;
    take?: number;
    skip?: number;
    distinct?: Prisma.WorkspaceScalarFieldEnum | Prisma.WorkspaceScalarFieldEnum[];
};
export type User$tasksArgs<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
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
export type User$eventsArgs<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    select?: Prisma.EventSelect<ExtArgs> | null;
    omit?: Prisma.EventOmit<ExtArgs> | null;
    include?: Prisma.EventInclude<ExtArgs> | null;
    where?: Prisma.EventWhereInput;
    orderBy?: Prisma.EventOrderByWithRelationInput | Prisma.EventOrderByWithRelationInput[];
    cursor?: Prisma.EventWhereUniqueInput;
    take?: number;
    skip?: number;
    distinct?: Prisma.EventScalarFieldEnum | Prisma.EventScalarFieldEnum[];
};
export type UserDefaultArgs<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    select?: Prisma.UserSelect<ExtArgs> | null;
    omit?: Prisma.UserOmit<ExtArgs> | null;
    include?: Prisma.UserInclude<ExtArgs> | null;
};
